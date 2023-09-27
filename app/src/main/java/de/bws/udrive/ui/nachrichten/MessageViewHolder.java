package de.bws.udrive.ui.nachrichten;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.bws.udrive.R;

public class MessageViewHolder extends RecyclerView.ViewHolder{
    private TextView tvMessageName;
    private TextView tvMessageETA;
    private TextView tvMessageComment;
    private TextView tvMessageStartTime;
    private TextView tvMessageDestination;
    private MessageAdapter adapter;

    public MessageViewHolder(@NonNull View itemView) {

        super(itemView);
        tvMessageName = itemView.findViewById(R.id.tvMessageName);
        tvMessageETA = itemView.findViewById(R.id.tvMessageETA);
        tvMessageComment = itemView.findViewById(R.id.tvMessageComment);
        tvMessageStartTime = itemView.findViewById(R.id.tvMessageStartTime);
        tvMessageDestination = itemView.findViewById(R.id.tvMessageDestination);
    }
    public MessageViewHolder linkAdapter(MessageAdapter adapter){
        this.adapter = adapter;
        return this;
    }

    public TextView getTvMessageName() {
        return tvMessageName;
    }

    public TextView getTvMessageETA() {
        return tvMessageETA;
    }

    public TextView getTvMessageComment() {
        return tvMessageComment;
    }

    public TextView getTvMessageStartTime() {
        return tvMessageStartTime;
    }

    public TextView getTvMessageDestination() {
        return tvMessageDestination;
    }

    public MessageAdapter getAdapter() {
        return adapter;
    }
}
