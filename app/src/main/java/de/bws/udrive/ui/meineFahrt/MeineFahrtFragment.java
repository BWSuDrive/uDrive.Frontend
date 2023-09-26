package de.bws.udrive.ui.meineFahrt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import de.bws.udrive.R;
import de.bws.udrive.databinding.FragmentMeinefahrtBinding;

public class MeineFahrtFragment extends Fragment {
    private List<MeineFahrtRequest> requestList = new ArrayList<MeineFahrtRequest>();
    private FragmentMeinefahrtBinding binding;
    private Button refreshButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MeineFahrtViewModel meineFahrtViewModel =
                new ViewModelProvider(this).get(MeineFahrtViewModel.class);

        binding = FragmentMeinefahrtBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMeineFahrt;
        meineFahrtViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        refreshButton = binding.btnDriverRefresh;
        refreshButton.setOnClickListener(driverRefreshListener);

        requestList.add(new MeineFahrtRequest("Niko", "Löwen", "110", "Wiesbaden Hauptbahnhof"));
        requestList.add(new MeineFahrtRequest("Fabian", "Haßa", "112", "Frankfurt Hauptbahnhof"));
        requestList.add(new MeineFahrtRequest("Lucas", "Christian", "116117", "Nürnberg"));

        return root;
    }
    private final View.OnClickListener driverRefreshListener = view -> {

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}