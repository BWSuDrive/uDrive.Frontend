package de.bws.udrive.ui.start.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.bws.udrive.R;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.PassengerRequest;
import de.bws.udrive.utilities.uDriveUtilities;

public class MeineFahrtAdapter extends RecyclerView.Adapter<MeineFahrtViewHolder>{

    private List<PassengerRequest> items;
    private LifecycleOwner owner;

    public MeineFahrtAdapter(List<PassengerRequest> items, LifecycleOwner owner)
    {
        this.items = items;
        this.owner = owner;
    }

    @NonNull
    @Override
    public MeineFahrtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meinefahrt_entry, parent, false);

        return new MeineFahrtViewHolder(view, parent.getContext(), this.owner).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MeineFahrtViewHolder holder, int position)
    {
        PassengerRequest current = items.get(position);

        String personName = current.getPerson().get("firstname").toString() + " " + current.getPerson().get("lastname").toString();
        holder.getTvPassengerName().setText(personName);

        double passengerLatitude = current.getCurrentLatitude();
        double passengerLongitude = current.getCurrentLongitude();

        double currentLatitude = General.getSignedInUser().getLatitude();
        double currentLongitude = General.getSignedInUser().getLongitude();

        double distanceKm = uDriveUtilities.calculateDistance(passengerLatitude, currentLatitude, passengerLongitude, currentLongitude, 0.0, 0.0) / 1000;
        holder.getTvPassengerDistance().setText(distanceKm + " km");
        holder.getTvPassengerPhone().setText(current.getPerson().get("phoneNumber").toString());
        holder.getTvPassengerFreitext().setText(current.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public List<PassengerRequest> getItems() { return items; }
}
