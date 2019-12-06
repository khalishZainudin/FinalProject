package my.edu.fsktm.um.finalproject.ForumTitle;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

import java.util.Date;

import my.edu.fsktm.um.finalproject.R;

public class MessagesAdapter extends FirestoreRecyclerAdapter<Messages, MessagesAdapter.MessagesHolder> {

    public MessagesAdapter(@NonNull FirestoreRecyclerOptions<Messages> options) { super(options); }

    @Override
    protected void onBindViewHolder(MessagesHolder holder, int position,  Messages model) {
        holder.textViewMessages.setText(model.getMessages());
        holder.textViewUser.setText(model.getUser());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm a");
        String testtimeStamp = (model.getTimePosted().toDate().toString());
        String testtimeStamp2 = simpleDateFormat.format(new Date(testtimeStamp));
        holder.timeStamp.setText(testtimeStamp2);
    }

    @NonNull
    @Override
    public MessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforuminterface,parent,false);
        return new MessagesHolder(view);
    }

    class MessagesHolder extends RecyclerView.ViewHolder{
        TextView textViewMessages;
        TextView textViewUser;
        TextView timeStamp;
        public MessagesHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessages = itemView.findViewById(R.id.tvDescription_reply);
            textViewUser = itemView.findViewById(R.id.tvUsername);
            timeStamp = itemView.findViewById(R.id.tvTimestampMessages);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
