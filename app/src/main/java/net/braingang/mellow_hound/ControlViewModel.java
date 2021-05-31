package net.braingang.mellow_hound;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ControlViewModel extends ViewModel {
    public static final String LOG_TAG = ControlViewModel.class.getName();

    private MutableLiveData<String> mutableCounter = new MutableLiveData<>();
    private MutableLiveData<String> mutableRunMode = new MutableLiveData<>();

    public ControlViewModel() {
        Log.i(LOG_TAG, "ControlViewModel ctor");
    }

    public LiveData<String> getCounter() {
        return mutableCounter;
    }

    public void setCounter(int observationCount, int fileCount) {
        String buffer = Integer.toString(observationCount) + ":" + Integer.toString(fileCount);
        mutableCounter.setValue(buffer);
    }

    public LiveData<String> getRunMode() {
        return mutableRunMode;
    }

    public void setRunMode(String arg) {
        mutableRunMode.setValue(arg);
    }
}
