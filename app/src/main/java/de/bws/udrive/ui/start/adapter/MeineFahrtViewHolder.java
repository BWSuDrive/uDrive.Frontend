package de.bws.udrive.ui.start.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.R;
import de.bws.udrive.ui.meineFahrt.MeineFahrtRequest;

public class MeineFahrtViewHolder extends RecyclerView.ViewHolder {
    private TextView tvPassengerName;
    private TextView tvPassengerDistance;
    private TextView tvPassengerPhone;
    private TextView tvPassengerFreitext;
    private ImageButton btnAccept;
    private ImageButton btnDecline;
    private MeineFahrtAdapter adapter;

    public MeineFahrtViewHolder(@NonNull View itemView) {

        super(itemView);
        tvPassengerName = itemView.findViewById(R.id.tvPassengerName);
        tvPassengerDistance = itemView.findViewById(R.id.tvPassengerDistance);
        tvPassengerPhone = itemView.findViewById(R.id.tvPassengerPhone);
        tvPassengerFreitext = itemView.findViewById(R.id.tvPassengerFreitext);
        btnAccept = itemView.findViewById(R.id.btnAccept);
        btnDecline = itemView.findViewById(R.id.btnDecline);
        btnAccept.setOnClickListener(view -> {

        });
        btnDecline.setOnClickListener(view ->{

        });

    }
    public MeineFahrtViewHolder linkAdapter(MeineFahrtAdapter adapter){
        this.adapter = adapter;
        return this;
    }
    public TextView getTvPassengerName() {
        return tvPassengerName;
    }

    public TextView getTvPassengerDistance() {
        return tvPassengerDistance;
    }

    public TextView getTvPassengerPhone() {
        return tvPassengerPhone;
    }

    public TextView getTvPassengerFreitext() {
        return tvPassengerFreitext;
    }

    public ImageButton getBtnAccept() {
        return btnAccept;
    }

    public ImageButton getBtnDecline() {
        return btnDecline;
    }

    public MeineFahrtAdapter getAdapter() {
        return adapter;
    }
}
