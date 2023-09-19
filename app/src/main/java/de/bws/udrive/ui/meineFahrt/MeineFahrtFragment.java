package de.bws.udrive.ui.meineFahrt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.bws.udrive.databinding.FragmentMeinefahrtBinding;

public class MeineFahrtFragment extends Fragment {

    private FragmentMeinefahrtBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MeineFahrtViewModel meineFahrtViewModel =
                new ViewModelProvider(this).get(MeineFahrtViewModel.class);

        binding = FragmentMeinefahrtBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMeineFahrt;
        meineFahrtViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}