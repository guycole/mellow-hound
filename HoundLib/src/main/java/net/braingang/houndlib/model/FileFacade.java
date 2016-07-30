package net.braingang.houndlib.model;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

/**
 *
 */
public class FileFacade {
    public static final String LOG_TAG = FileFacade.class.getName();

    public void writeObservation(Observation observation, Context context) {
        String fileName = UUID.randomUUID().toString();
        String fullName = context.getExternalFilesDir(null).getAbsolutePath() + "/" + fileName;
        Log.i(LOG_TAG, "write:" + fullName);

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(fullName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(observation);
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch(Exception exception) {
                // empty
            }
        }
    }
}
