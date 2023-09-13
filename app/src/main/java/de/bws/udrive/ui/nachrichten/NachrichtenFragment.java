package de.bws.udrive.ui.nachrichten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.bws.udrive.databinding.FragmentNachrichtenBinding;

public class NachrichtenFragment extends Fragment {

    private FragmentNachrichtenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NachrichtenViewModel nachrichtenViewModel =
                new ViewModelProvider(this).get(NachrichtenViewModel.class);

        binding = FragmentNachrichtenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNachrichten;
        nachrichtenViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}