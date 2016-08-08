package net.braingang.houndlib.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import net.braingang.houndlib.Constant;

public class NoiseService extends IntentService {
    public static final String LOG_TAG = NoiseService.class.getName();

    private static final String ACTION_NOTIFICATION = "net.braingang.houndlib.service.action.NOTIFICATION";

    /**
     * play notification
     * @param context
     */
    public static void startActionNotification(Context context) {
        Intent intent = new Intent(context, NoiseService.class);
        intent.setAction(ACTION_NOTIFICATION);
        context.startService(intent);
    }

    public NoiseService() {
        super("NoiseService");
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

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFICATION.equals(action)) {
                handleActionNotification();
            }
        }
    }

    private void handleActionNotification() {
        try {
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);
            ringtone.play();

            while (ringtone.isPlaying()) {
                Thread.sleep(Constant.QUARTER_SECOND);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
