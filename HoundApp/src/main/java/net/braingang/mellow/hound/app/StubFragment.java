package net.braingang.mellow.hound.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.braingang.mellow.hound.R;

/**
 *
 */
public class StubFragment extends Fragment {
    public static final String LOG_TAG = StubFragment.class.getName();

    private MainActivityListener listener;

    public static StubFragment newInstance() {
        return new StubFragment();
    }

    public StubFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stub, container, false);
    }
}