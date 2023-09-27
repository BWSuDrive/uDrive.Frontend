package de.bws.udrive.ui.start.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.bws.udrive.R;
import de.bws.udrive.ui.start.model.AvailableTours;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>
{
    private List<AvailableTours> items;

    public HomeAdapter(List<AvailableTours> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_entry, parent, false);

        return new HomeViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position)
    {
        AvailableTours current = items.get(position);
        holder.getUserName().setText(current.getPerson().toString());
        holder.getDestination().setText(current.getPlannedDrive().getDestination());
        holder.getEta().setText(current.getPlannedDrive().getEta() + " Uhr");
        holder.getDistance().setText(current.getPlannedDrive().getDistance() + " km");
        holder.getComment().setText((current.getPlannedDrive().getMessage() == "") ? "N/A" : current.getPlannedDrive().getMessage());
    }

    @Override
    public int getItemCount() { return items.size(); }
}
