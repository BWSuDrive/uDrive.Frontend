package de.bws.udrive.ui.meineFahrt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.bws.udrive.R;
import de.bws.udrive.databinding.FragmentMeinefahrtBinding;
import de.bws.udrive.ui.start.adapter.MeineFahrtAdapter;
import de.bws.udrive.utilities.handler.PassengerRequestsHandler;
import de.bws.udrive.utilities.model.PassengerRequest;

public class MeineFahrtFragment extends Fragment {
    private FragmentMeinefahrtBinding binding;
    private Button refreshButton;
    private RecyclerView rvPassengerList;
    private MeineFahrtAdapter fahrtAdapter;
    private PassengerRequestsHandler passengerRequestsHandler;

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
        rvPassengerList = binding.rvPassengerList;

        this.rvPassengerList.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private final View.OnClickListener driverRefreshListener = view -> {
        sendAPIRequest();
    };

    private void sendAPIRequest()
    {
        this.passengerRequestsHandler = new PassengerRequestsHandler();
        passengerRequestsHandler.getFinishedState().observe(this, observeStateChange);
        passengerRequestsHandler.handle();
    }

    private final Observer<Boolean> observeStateChange = isFinished -> {
        if(isFinished)
        {
            if (!passengerRequestsHandler.requestsAvailable())
            {
                Toast.makeText(getContext(), passengerRequestsHandler.getInformationString(), Toast.LENGTH_LONG).show();
            }
            else
            {
                showAvailablePassengers();
            }
        }
    };

    private void showAvailablePassengers()
    {
        this.fahrtAdapter = new MeineFahrtAdapter(passengerRequestsHandler.getAvailablePassengers());
        this.rvPassengerList.setAdapter(this.fahrtAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}