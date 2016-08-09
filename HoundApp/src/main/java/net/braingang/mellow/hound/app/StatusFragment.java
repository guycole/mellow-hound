package net.braingang.mellow.hound.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import net.braingang.houndlib.Personality;
import net.braingang.houndlib.model.FileFacade;
import net.braingang.mellow.hound.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class StatusFragment extends Fragment {
    public static final String LOG_TAG = StatusFragment.class.getName();

    private MainActivityListener listener;

    private Button refreshButton;
    private TextView tvRunMode;
    private TextView tvSampleTime;
    private TextView tvUploadPopulation;
    private TextView tvWaitPopulation;

    public static StatusFragment newInstance() {
        return new StatusFragment();
    }

    public StatusFragment() {
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
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        refreshButton = (Button) view.findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateDisplay();
            }
        });

        tvRunMode = (TextView) view.findViewById(R.id.operating_mode);
        tvSampleTime = (TextView) view.findViewById(R.id.last_sample_time);
        tvUploadPopulation = (TextView) view.findViewById(R.id.upload_population);
        tvWaitPopulation = (TextView) view.findViewById(R.id.waiting_population);

        updateDisplay();

        return view;
    }

    private void updateDisplay() {
        tvRunMode.setText(Personality.runMode.toString());

        String sampleTime = "awaiting update";
        if (Personality.currentLocation != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            sampleTime = sdf.format(new Date(Personality.currentLocation.getTime()));
        }

        tvSampleTime.setText(sampleTime);

        tvUploadPopulation.setText(Personality.uploadCounter.toString());

        FileFacade fileFacade = new FileFacade();
        int waitPop = fileFacade.getOutboundObservation(getActivity()).length;

        tvWaitPopulation.setText(Integer.toString(waitPop));
    }
}