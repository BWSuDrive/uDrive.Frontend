package de.bws.udrive.ui.nachrichten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.databinding.FragmentNachrichtenBinding;
import de.bws.udrive.utilities.handler.PassengerRequestsHandler;

public class NachrichtenFragment extends Fragment {

    private FragmentNachrichtenBinding binding;
    private MessageAdapter messageAdapter;
    private RecyclerView rvMessageList;
    private Button refreshButton;
    private PassengerRequestsHandler passengerRequestsHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NachrichtenViewModel nachrichtenViewModel =
                new ViewModelProvider(this).get(NachrichtenViewModel.class);

        binding = FragmentNachrichtenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        refreshButton = binding.btnMessageRefresh;
        refreshButton.setOnClickListener(messageRefreshListener);
        rvMessageList = binding.rvMessageList;
        this.rvMessageList.setAdapter(this.messageAdapter);


        return root;
    }
    private final View.OnClickListener messageRefreshListener = view -> {
        sendAPIRequest();
    };

    private void sendAPIRequest() {
        this.passengerRequestsHandler = new PassengerRequestsHandler();
        passengerRequestsHandler.handle();
        passengerRequestsHandler.getFinishedState().observe(this, observeStateChange);
    }
    Observer<Boolean> observeStateChange = isFinished ->
    {
        if (!passengerRequestsHandler.requestsAvailable())
            Toast.makeText(getContext(), passengerRequestsHandler.getInformationString(), Toast.LENGTH_LONG).show();
        else
            showAvailableMessages();
    };
    private void showAvailableMessages() {
        this.messageAdapter = new MessageAdapter(passengerRequestsHandler.getAvailablePassengers());
        this.rvMessageList.setAdapter(this.messageAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}