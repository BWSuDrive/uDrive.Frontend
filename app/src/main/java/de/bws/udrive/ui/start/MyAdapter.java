package de.bws.udrive.ui.start;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    //RealmList<fahrer> fahrerResults;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fahrerOutput;
        TextView startendOutput;
        TextView etaOutput;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fahrerOutput = itemView.findViewById(R.id.fahreroutput);
            startendOutput = itemView.findViewById(R.id.startzieloutput);
            etaOutput = itemView.findViewById(R.id.etaoutput);
        }
    }
}
