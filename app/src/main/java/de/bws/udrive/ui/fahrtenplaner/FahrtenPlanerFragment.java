package de.bws.udrive.ui.fahrtenplaner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.bws.udrive.databinding.FragmentFahrtenplanerBinding;

public class FahrtenPlanerFragment extends Fragment {

    private FragmentFahrtenplanerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FahrtenPlanerViewModel fahrtenPlanerViewModel = new ViewModelProvider(this).get(FahrtenPlanerViewModel.class);

        binding = FragmentFahrtenplanerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFahrtenPlaner;
        fahrtenPlanerViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}