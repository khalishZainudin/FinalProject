package my.edu.fsktm.um.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private List<Messages> messagesList;
    private Context mCtx;

    public MessagesAdapter(Context mCtx, List<Messages> messagesList) {
        this.mCtx = mCtx;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_messages, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Messages messages = messagesList.get(position);

        holder.textViewName.setText(messages.getName());
        holder.textViewMessages.setText(messages.getMessages());
        holder.textViewtimeStamp.setText(messages.getTime());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewMessages,textViewtimeStamp;

        public MessagesViewHolder(View messageView) {
            super(messageView);
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewMessages = itemView.findViewById(R.id.textview_messages);
            textViewtimeStamp = itemView.findViewById(R.id.textView_timestamp);
        }
    }
}

