package net.braingang.houndlib.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import net.braingang.houndlib.Constant;
import net.braingang.houndlib.model.FileFacade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Hourly start from AlarmManager
 */
public class OutboundService extends Service {
    public static final String LOG_TAG = OutboundService.class.getName();

    public static final int REQUEST_CODE = 7890;

    public static final String INTENT_ALARM = "IntentAlarm";

    private FileFacade fileFacade = new FileFacade();
    private Thread outboundThread = null;

    public static void startOutbound(Context context, long period) {
        Intent intent = new Intent(context, OutboundService.class);
        intent.setAction(INTENT_ALARM);

        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), period, pendingIntent);
    }

    public OutboundService() {
        // empty
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate entry");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy entry");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not used by local service
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        final String action = intent.getAction();
        if (action == null) {
            Log.d(LOG_TAG, "onStart:null");
        } else {
            Log.d(LOG_TAG, "onStart:" + action);

            if (action.equals(INTENT_ALARM)) {
                if ((outboundThread != null) && (outboundThread.isAlive())) {
                    Log.d(LOG_TAG, "skipping thread start");
                } else {
                    Log.d(LOG_TAG, "starting thread");
                    outboundThread = new OutboundThread();
                    outboundThread.start();
                }
            }
        }

        return START_NOT_STICKY;
    }

    class UploadListener implements TransferListener {
        @Override
        public void onStateChanged(int id, TransferState state) {
            Log.i(LOG_TAG, "onStateChanged:" + id + ":" + state);
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.i(LOG_TAG, "onProgressChanged:" + id + ":" + bytesCurrent + ":" + bytesTotal);
        }

        @Override
        public void onError(int id, Exception exception) {
            exception.printStackTrace();
        }
    }

    class OutboundThread extends Thread {
        private ArrayList<TransferObserver> observerList = new ArrayList<TransferObserver>();
        private UploadListener uploadListener = new UploadListener();

        private TransferUtility transferUtility;
        private CognitoCachingCredentialsProvider credentialsProvider;

        public void napTime(long duration) {
            Log.d(LOG_TAG, "nap time");
            try {
                sleep(duration);
            } catch(Exception exception) {
                Log.d(LOG_TAG, "sleep exception");
            }
        }

        public void completionPoll() {
            int attemptCounter = 33;
            boolean whileFlag = true;
            while ((whileFlag) && (--attemptCounter > 0)) {
                whileFlag = false;
                for (TransferObserver observer : observerList) {
                    if (observer.getState() == TransferState.COMPLETED) {
                        Log.d(LOG_TAG, "complete:" + observer.getId() + ":" + observer.getAbsoluteFilePath());
                        File file = new File(observer.getAbsoluteFilePath());
                        file.delete();
                    } else if (observer.getState() == TransferState.FAILED) {
                        Log.d(LOG_TAG, "failure:" + observer.getId() + ":" + observer.getAbsoluteFilePath());
                    } else {
                        Log.d(LOG_TAG, "incomplete:" + observer.getId() + ":" + observer.getAbsoluteFilePath());
                        whileFlag = true;
                    }
                }

                if (whileFlag) {
                    napTime(Constant.ONE_MINUTE);
                }
            }
        }

        public void run() {
            credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), Constant.COGNITO_POOL_ID, Regions.US_EAST_1);

            AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
            transferUtility = new TransferUtility(s3, getApplicationContext());

            Map<String, String> userMetaData = new HashMap<String, String>();
            userMetaData.put("PLATFORM", "Android");
            userMetaData.put("PROJECT", "MellowHound");
            userMetaData.put("VERSION", "1");

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setUserMetadata(userMetaData);

            while (true) {
                Log.d(LOG_TAG, "xxx outbound thread run xxx");

                File[] candidates = fileFacade.getOutboundObservation(getBaseContext());
                Log.d(LOG_TAG, "candidate pop:" + candidates.length);

                if (candidates.length < 1) {
                    napTime(Constant.ONE_HOUR);
                } else {
                    observerList.clear();

                    for (File candidate:candidates) {
                        TransferObserver observer = transferUtility.upload(Constant.BUCKET_NAME, candidate.getName(), candidate, objectMetaData);
                        observer.setTransferListener(uploadListener);
                        observerList.add(observer);
                    }

                    completionPoll();
                }
            }
        }
    }
}