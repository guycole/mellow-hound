package net.braingang.mellow_hound;

public interface HoundListener {

    void onCollectionStart();

    void onCollectionStop();

    void onAwsUpload();
}
