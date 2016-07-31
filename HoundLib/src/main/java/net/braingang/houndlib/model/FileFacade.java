package net.braingang.houndlib.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(fullName));
            bw.write(observation.toJson(context).toString());
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch(Exception exception) {
                // empty
            }
        }
    }

    public File[] getOutboundObservation(Context context) {
        File root = new File(context.getExternalFilesDir(null).getAbsolutePath());
        return root.listFiles();
    }
}
