package net.braingang.houndlib.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
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
import net.braingang.houndlib.Personality;
import net.braingang.houndlib.Secret;
import net.braingang.houndlib.model.FileFacade;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OutboundService extends IntentService {
    public static final String LOG_TAG = OutboundService.class.getName();

    public static final int REQUEST_CODE = 314159;

    public static final String ACTION_ALARM = "net.braingang.houndlib.service.action.ALARM";

    private FileFacade fileFacade = new FileFacade();
    private ArrayList<TransferObserver> observerList = new ArrayList<TransferObserver>();
    private UploadListener uploadListener = new UploadListener();

    private TransferUtility transferUtility;
    private CognitoCachingCredentialsProvider credentialsProvider;

    public OutboundService() {
        super("OutboundService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "xxx xxx onCreate xxx xxx");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "xxx xxx onDestroy xxx xxx");
    }

    /**
     * start w/alarm manager
     * @param context
     * @param period
     */
    public static void startAlarmManager(Context context, long period) {
        Intent intent = new Intent(context, OutboundService.class);
        intent.setAction(ACTION_ALARM);

        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), period, pendingIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent entry");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ALARM.equals(action)) {
                handleActionAlarm();
            }
        }
    }

    private void handleActionAlarm() {
        Log.d(LOG_TAG, "handleActionAlarm");

        credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), Secret.COGNITO_POOL_ID, Regions.US_EAST_1);

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());

        buildObserverList();

        completionPoll();

        //NoiseService.startActionNotification(this);
    }

    private void completionPoll() {
        int attemptCounter = 33;
        boolean whileFlag = true;
        while ((whileFlag) && (--attemptCounter > 0)) {
            whileFlag = false;
            for (TransferObserver observer : observerList) {
                if (observer.getState() == TransferState.COMPLETED) {
                    Log.d(LOG_TAG, "complete:" + observer.getId() + ":" + observer.getAbsoluteFilePath());
                    Personality.uploadCounter += 1;
                    Log.d(LOG_TAG, "upload counter:" + Personality.uploadCounter);
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

    private void buildObserverList() {
        Map<String, String> userMetaData = new HashMap<String, String>();
        userMetaData.put("PLATFORM", "Android");
        userMetaData.put("PROJECT", "MellowHound");
        userMetaData.put("VERSION", "1");

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setUserMetadata(userMetaData);

        File[] candidates = fileFacade.getOutboundObservation(getBaseContext());
        Log.d(LOG_TAG, "candidate pop:" + candidates.length);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String directory = "hound/hound-" + sdf.format(new Date()) + "/";

        for (File candidate:candidates) {
            String fileName = directory + candidate.getName();
            TransferObserver observer = transferUtility.upload(Secret.BUCKET_NAME, fileName, candidate, objectMetaData);
            observer.setTransferListener(uploadListener);
            observerList.add(observer);
        }
    }

    private void napTime(long duration) {
        Log.d(LOG_TAG, "nap time");
        try {
            Thread.sleep(duration);
        } catch(Exception exception) {
            Log.d(LOG_TAG, "sleep exception");
        }
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
}
