package net.braingang.mellow_hound;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ControlViewModel extends ViewModel {
    public static final String LOG_TAG = ControlViewModel.class.getName();

    private MutableLiveData<String> mutableCounter = new MutableLiveData<>();
    private MutableLiveData<String> mutableRunMode = new MutableLiveData<>();

    public ControlViewModel() {
        // empty
    }

    public LiveData<String> getCounter() {
        return mutableCounter;
    }

    public void setCounter(int observationCount, int fileCount) {
        String buffer = Integer.toString(observationCount) + ":" + Integer.toString(fileCount);
        mutableCounter = new MutableLiveData<String>(buffer);
    }

    public LiveData<String> getRunMode() {
        return mutableRunMode;
    }

    public void setRunMode(String runMode) {
        mutableRunMode = new MutableLiveData<String>(runMode);
    }
}
