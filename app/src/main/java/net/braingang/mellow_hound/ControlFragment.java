package net.braingang.mellow_hound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.braingang.mellow_hound.databinding.FragmentControlBinding;

public class ControlFragment extends Fragment {
    public static final String LOG_TAG = ControlFragment.class.getName();

    private ControlViewModel controlViewModel;
    private FragmentControlBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentControlBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HoundListener houndListener = (MainActivity) getActivity();

        controlViewModel = new ViewModelProvider(requireActivity()).get(ControlViewModel.class);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houndListener.onCollectionStart();
            }
        });

        binding.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houndListener.onCollectionStop();
            }
        });

        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houndListener.onAwsUpload();
            }
        });

        controlViewModel.getRunMode().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@NonNull String arg) {
                Log.i(LOG_TAG, "onChanged:" + arg);
                binding.tvMode.setText(arg);
            }
        });

        controlViewModel.getCounter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@NonNull String arg) {
                Log.i(LOG_TAG, "onChanged:" + arg);
                binding.tvCounter.setText(arg);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
