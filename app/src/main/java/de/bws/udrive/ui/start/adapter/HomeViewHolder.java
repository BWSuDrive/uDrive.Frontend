package de.bws.udrive.ui.start.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.R;
import de.bws.udrive.utilities.handler.TakeAwayHandler;
import de.bws.udrive.utilities.model.General;
import de.bws.udrive.utilities.model.RequestTakeAway;

public class HomeViewHolder extends RecyclerView.ViewHolder
{
    private Context context;
    private LifecycleOwner owner;
    private HomeAdapter homeAdapter;

    private TextView userName;
    private TextView destination;
    private TextView eta;
    private TextView distance;
    private TextView comment;

    /* Input-Feld vom Dialog */
    private EditText freeText;

    /* Handler */
    private TakeAwayHandler takeAwayHandler;

    private Button btnRequest;
    public HomeViewHolder(@NonNull View itemView, Context context, LifecycleOwner owner)
    {
        super(itemView);

        this.context = context;
        this.owner = owner;

        this.userName = itemView.findViewById(R.id.tvDriverName);
        this.destination = itemView.findViewById(R.id.tvDriverDestination);
        this.eta = itemView.findViewById(R.id.tvDriverETA);
        this.distance = itemView.findViewById(R.id.tvDriverDistance);
        this.comment = itemView.findViewById(R.id.tvDriverComment);
        this.btnRequest = itemView.findViewById(R.id.btnDriverRequest);
        this.btnRequest.setOnClickListener(btnClicked);
    }

    public HomeViewHolder linkAdapter(HomeAdapter homeAdapter)
    {
        this.homeAdapter = homeAdapter;
        return this;
    }

    private View.OnClickListener btnClicked = view ->
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        this.freeText = new EditText(this.context);
        freeText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle("Kommentar hinzufügen...");
        this.freeText.setHint("Adresse eingeben...");
        builder.setView(freeText);
        builder.setPositiveButton("Anfrage senden", this.okClicked);
        builder.setNegativeButton("Abbrechen", this.cancelClicked);

        builder.show();
    };

    /* Wenn OK geklickt wird, API Call */
    private final DialogInterface.OnClickListener okClicked = (dialogInterface, i) ->
    {
        this.takeAwayHandler = new TakeAwayHandler();

        String idTourPlan = homeAdapter.getItems().get(getAbsoluteAdapterPosition()).getPlannedDrive().getId();
        String message = freeText.getText().toString();

        double currentLatitude = General.getSignedInUser().getLatitude();
        double currentLongitude = General.getSignedInUser().getLongitude();

        RequestTakeAway takeAway = new RequestTakeAway(idTourPlan, message, currentLatitude, currentLongitude);

        takeAwayHandler.getFinishedState().observe(owner, this.observeStateChange);
        takeAwayHandler.handle(takeAway);

    };

    /* Wenn Abbruch gedrück wird, Dialog schließen */
    private final DialogInterface.OnClickListener cancelClicked = (dialogInterface, i) -> dialogInterface.cancel();

    private final Observer<Boolean> observeStateChange = isFinished ->
    {
        if(isFinished)
        {
            if(takeAwayHandler.requestSuccessful())
            {
                Toast.makeText(this.context, "Anfrage erfolgreich!", Toast.LENGTH_LONG).show();
                this.btnRequest.setClickable(false);
                this.btnRequest.setBackgroundColor(Color.GRAY);
            }
        }
    };

    /* Field Getter */
    public TextView getUserName() { return userName; }

    public TextView getDestination() { return destination; }

    public TextView getEta() { return eta; }

    public TextView getDistance() { return distance; }

    public TextView getComment() { return comment; }
}
