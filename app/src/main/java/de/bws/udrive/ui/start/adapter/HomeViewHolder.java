package de.bws.udrive.ui.start.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.HomeActivity;
import de.bws.udrive.R;

public class HomeViewHolder extends RecyclerView.ViewHolder
{
    private HomeAdapter homeAdapter;

    private TextView userName;
    private TextView destination;
    private TextView eta;
    private TextView distance;
    private TextView comment;
    public HomeViewHolder(@NonNull View itemView)
    {
        super(itemView);

        this.userName = itemView.findViewById(R.id.tvDriverName);
        this.destination = itemView.findViewById(R.id.tvDriverDestination);
        this.eta = itemView.findViewById(R.id.tvDriverETA);
        this.distance = itemView.findViewById(R.id.tvDriverDistance);
        this.comment = itemView.findViewById(R.id.tvDriverComment);
    }

    public HomeViewHolder linkAdapter(HomeAdapter homeAdapter)
    {
        this.homeAdapter = homeAdapter;
        return this;
    }

    /* Field Getter */
    public TextView getUserName() { return userName; }

    public TextView getDestination() { return destination; }

    public TextView getEta() { return eta; }

    public TextView getDistance() { return distance; }

    public TextView getComment() { return comment; }
}
