package de.bws.udrive.ui.start.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.bws.udrive.R;
import de.bws.udrive.utilities.model.PassengerRequest;

public class MeineFahrtAdapter extends RecyclerView.Adapter<MeineFahrtViewHolder>{

    List<PassengerRequest> items;

    public MeineFahrtAdapter(List<PassengerRequest> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MeineFahrtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meinefahrt_entry, parent, false);

        return new MeineFahrtViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MeineFahrtViewHolder holder, int position) {
        holder.getTvPassengerName().setText(items.get(position).getFirstname() +" "+items.get(position).getLastname());
        holder.getTvPassengerDistance().setText("WIP");
        holder.getTvPassengerPhone().setText(items.get(position).getPhoneNumber());
        holder.getTvPassengerFreitext().setText(items.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
