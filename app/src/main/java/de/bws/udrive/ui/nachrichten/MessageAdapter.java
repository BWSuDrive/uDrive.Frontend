package de.bws.udrive.ui.nachrichten;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.bws.udrive.R;
import de.bws.udrive.utilities.model.PassengerRequest;
import de.bws.udrive.utilities.uDriveUtilities;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private List<PassengerRequest> items;

    public MessageAdapter(List<PassengerRequest> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_entry, parent, false);

        return new MessageViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position)
    {
        PassengerRequest current = items.get(position);

        String userName = current.getPerson().get("firstname").toString() + " " + current.getPerson().get("lastname").toString();

        holder.getTvMessageName().setText(userName);
        holder.getTvMessageComment().setText("holt dich ab");
        //holder.getTvMessageDestination().setText(current.getTourPlan().get("destination").toString());
        holder.getTvMessageDestination().setText("WiP");
        //holder.getTvMessageStartTime().setText(uDriveUtilities.parseString(current.getTourPlan().get("departure").toString()));
        holder.getTvMessageStartTime().setText("WiP");
        //holder.getTvMessageETA().setText("ETA: " + current.getTourPlan().get("eta").toString() + " Uhr");
        holder.getTvMessageETA().setText("WiP");
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
