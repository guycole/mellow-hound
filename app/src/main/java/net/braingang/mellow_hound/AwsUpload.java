package net.braingang.mellow_hound;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

import java.io.File;
import java.util.List;

public class AwsUpload {
    public static final String LOG_TAG = AwsUpload.class.getName();

    public void writer(Context context, File[] files) {
        AWSCredentials credentials = new BasicAWSCredentials(Personality.AwsAccessKey, Personality.AwsSecretKey);
        AmazonS3 s3 = new AmazonS3Client(credentials);

        TransferUtility transferUtility = new TransferUtility(s3, context);

        for (File file:files) {
            String key = file.getName();
            Log.i(LOG_TAG, "uploading:" + key);

            TransferObserver observer = transferUtility.upload(Personality.AwsBucketName, key, file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    Log.i(LOG_TAG, "onStateChanged:" + id);
                    // do something
                    //progress.hide();
                    //path.setText("ID "+id+"\nState "+state.name()+"\nImage ID "+OBJECT_KEY);
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    Log.i(LOG_TAG, "onProgressChanged:" + id);
                    //int percentage = (int) (bytesCurrent / bytesTotal * 100);
                    //progress.setProgress(percentage);
                    //Display percentage transfered to user
                }

                @Override
                public void onError(int id, Exception ex) {
                    // do something
                    Log.e(LOG_TAG, "Error:" + id + ":" + ex);
                }
            });
        }
    }

/*
    public void xx() {


        java.security.Security.setProperty("networkaddress.cache.ttl" , "60");

        s3.setRegion(Region.getRegion(Regions.US_EAST_1));
        s3.setEndpoint("https://s3-us-west-2.amazonaws.com/");

        List<Bucket> buckets=s3.listBuckets();
        for (Bucket bucket:buckets) {
            Log.i(LOG_TAG, "Bucket:" + bucket.getName() + " Date " + bucket.getCreationDate());
            Log.e(LOG_TAG, "Size:" + s3.listBuckets().size());
        }

        TransferUtility transferUtility = new TransferUtility(s3, getApplicationContext());
        UPLOADING_IMAGE = new File(Environment.getExternalStorageDirectory().getPath() + "/Screenshot.png");
        TransferObserver observer = transferUtility.upload(MY_BUCKET,OBJECT_KEY, UPLOADING_IMAGE);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                // do something
                progress.hide();
                path.setText("ID "+id+"\nState "+state.name()+"\nImage ID "+OBJECT_KEY);
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
                progress.setProgress(percentage);
                //Display percentage transfered to user
            }

            @Override
            public void onError(int id, Exception ex) {
                // do something
                Log.e(LOG_TAG, "Error  ",""+ex );
            }
    });
 */
}
