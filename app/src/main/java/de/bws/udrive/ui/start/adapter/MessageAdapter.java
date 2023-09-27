package de.bws.udrive.ui.start.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.bws.udrive.R;
import de.bws.udrive.utilities.model.PassengerRequest;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    List<PassengerRequest> items;

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
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.getTvMessageName().setText(items.get(position).getFirstname() +" " + items.get(position).getLastname());
        holder.getTvMessageComment().setText("holt dich ab");
        //holder.getTvMessageDestination().setText(items.get(position).get());
        //holder.getTvMessageStartTime().setText(items.get(position).get());
        //holder.getTvMessageETA().setText("ETA " + items.get(position).getTvMessageETA());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
