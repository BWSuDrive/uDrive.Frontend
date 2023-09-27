package de.bws.udrive.ui.start.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import de.bws.udrive.R;
import de.bws.udrive.ui.meineFahrt.MeineFahrtRequest;
import de.bws.udrive.utilities.handler.RequestReactHandler;
import de.bws.udrive.utilities.model.PassengerRequest;

public class MeineFahrtViewHolder extends RecyclerView.ViewHolder
{
    private LifecycleOwner owner;
    private Context context;
    private TextView tvPassengerName;
    private TextView tvPassengerDistance;
    private TextView tvPassengerPhone;
    private TextView tvPassengerFreitext;
    private ImageButton btnAccept;
    private ImageButton btnDecline;
    private MeineFahrtAdapter adapter;

    private RequestReactHandler requestReactHandler;

    public MeineFahrtViewHolder(@NonNull View itemView, Context context, LifecycleOwner owner) {

        super(itemView);

        this.context = context;
        this.owner = owner;

        tvPassengerName = itemView.findViewById(R.id.tvPassengerName);
        tvPassengerDistance = itemView.findViewById(R.id.tvPassengerDistance);
        tvPassengerPhone = itemView.findViewById(R.id.tvPassengerPhone);
        tvPassengerFreitext = itemView.findViewById(R.id.tvPassengerFreitext);
        btnAccept = itemView.findViewById(R.id.btnAccept);
        btnDecline = itemView.findViewById(R.id.btnDecline);
        btnAccept.setOnClickListener(btnAcceptListener);
        btnDecline.setOnClickListener(btnDeniedListener);

    }
    public MeineFahrtViewHolder linkAdapter(MeineFahrtAdapter adapter)
    {
        this.adapter = adapter;
        return this;
    }
    public TextView getTvPassengerName() {
        return tvPassengerName;
    }

    private final View.OnClickListener btnAcceptListener = view ->
    {
        PassengerRequest currentSelectedRequest = adapter.getItems().get(getAbsoluteAdapterPosition());

        this.requestReactHandler = new RequestReactHandler();

        Log.i("uDrive.ACCEPT", new Gson().toJson(currentSelectedRequest));

        requestReactHandler.getFinishedState().observe(owner, this.acceptedStateChanged);
        requestReactHandler.handle(currentSelectedRequest, true);
    };

    private final View.OnClickListener btnDeniedListener = view ->
    {
        PassengerRequest currentSelectedRequest = adapter.getItems().get(getAbsoluteAdapterPosition());

        this.requestReactHandler = new RequestReactHandler();

        requestReactHandler.getFinishedState().observe(owner, this.deniedStateChanged);
        requestReactHandler.handle(currentSelectedRequest, false);
    };

    private final Observer<Boolean> acceptedStateChanged = isFinished ->
    {
        if(isFinished)
        {
            if(requestReactHandler.requestSuccessful())
            {
                Toast.makeText(this.context, "Die Anfrage wurde erfolgreich akzeptiert!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, requestReactHandler.getInformationString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    private final Observer<Boolean> deniedStateChanged = isFinished ->
    {
        if(isFinished)
        {
            if(requestReactHandler.requestSuccessful())
            {
                Toast.makeText(this.context, "Die Anfrage wurde erfolgreich abgelehnt!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, requestReactHandler.getInformationString(), Toast.LENGTH_LONG).show();
            }
        }
    };

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
