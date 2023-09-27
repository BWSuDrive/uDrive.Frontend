package de.bws.udrive.ui.start;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import de.bws.udrive.databinding.FragmentStartBinding;
import de.bws.udrive.utilities.handler.DriveRequestsHandler;
import de.bws.udrive.utilities.model.DriveRequest;
import de.bws.udrive.utilities.model.General;

public class StartFragment extends Fragment {

    private final String TAG = "uDrive." + getClass().getSimpleName();
    private FragmentStartBinding binding;
    private MaterialButton refreshFahrer;
    private DriveRequestsHandler driveRequestsHandler;
    private TextView tvAnfragen;
    private RecyclerView fahrerListe;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StartViewModel startViewModel =
                new ViewModelProvider(this).get(StartViewModel.class);

        binding = FragmentStartBinding.inflate(inflater, container, false);

        refreshFahrer = binding.refreshFahrer;
        refreshFahrer.setOnClickListener(refreshListener);
        tvAnfragen = binding.textStart;
        fahrerListe = binding.fahrerliste;

        return binding.getRoot();
    }

    private View.OnClickListener refreshListener = view ->
    {
        sendAPIRequest();
    };

    private void sendAPIRequest()
    {
        Log.i(TAG, "Sending API Request");
        double currentLatitude = General.getSignedInUser().getLatitude();
        double currentLongitude = General.getSignedInUser().getLongitude();

        Log.i(TAG, "Latitude: " + currentLatitude);
        Log.i(TAG, "Longitude: " + currentLongitude);

        DriveRequest driveRequest = new DriveRequest(currentLatitude, currentLongitude);
        this.driveRequestsHandler = new DriveRequestsHandler();

        driveRequestsHandler.handle(driveRequest);
        driveRequestsHandler.getFinishedState().observe(this, observeStatChange);
    }

    private Observer<Boolean> observeStatChange = isFinished -> {
        if(!driveRequestsHandler.requestsAvailable())
        {
            Toast.makeText(getContext(), driveRequestsHandler.getInformationString(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Keine Fahrten verfügbar!");
        }
        else
        {
            showAvailableDrivers();
        }
    };

    private void showAvailableDrivers()
    {
        tvAnfragen.setText("Size of List: " + driveRequestsHandler.getAvailableTours().size());
    }

}

/* Back Up
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StartViewModel startViewModel =
                new ViewModelProvider(this).get(StartViewModel.class);

        binding = FragmentStartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStart;
        startViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
 */