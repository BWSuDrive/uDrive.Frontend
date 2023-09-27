package de.bws.udrive.ui.nachrichten;

import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.databinding.FragmentNachrichtenBinding;
import de.bws.udrive.ui.start.adapter.MeineFahrtAdapter;
import de.bws.udrive.ui.start.adapter.MessageAdapter;
import de.bws.udrive.utilities.handler.MessageRequestsHandler;
import de.bws.udrive.utilities.handler.PassengerRequestsHandler;

public class NachrichtenFragment extends Fragment {

    private FragmentNachrichtenBinding binding;
    private MessageAdapter messageAdapter;
    private RecyclerView rvMessageList;
    private Button refreshButton;
    private MessageRequestsHandler messageRequestsHandler;

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
        this.messageRequestsHandler = new MessageRequestsHandler();
        messageRequestsHandler.handle();
        messageRequestsHandler.getFinishedState().observe(this, observeStateChange);
    }
    Observer<Boolean> observeStateChange = isFinished -> {
        if (!messageRequestsHandler.requestsAvailable()) {
            Toast.makeText(getContext(), messageRequestsHandler.getInformationString(), Toast.LENGTH_LONG).show();
        } else
            showAvailableMessages();
    };
    private void showAvailableMessages() {
        this.messageAdapter = new MessageAdapter(messageRequestsHandler.getAvailableMessages());
        this.rvMessageList.setAdapter(this.messageAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}