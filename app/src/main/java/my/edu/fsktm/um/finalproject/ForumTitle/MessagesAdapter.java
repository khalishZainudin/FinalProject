package my.edu.fsktm.um.finalproject.ForumTitle;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.AddReplyFragment;

import my.edu.fsktm.um.finalproject.R;

public class MessagesAdapter extends FirestoreRecyclerAdapter<Messages, MessagesAdapter.MessagesHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MessagesAdapter context;
    Bundle extrasFromInterface;

    public MessagesAdapter(@NonNull FirestoreRecyclerOptions<Messages> options,Bundle bundle) {
        super(options);
        extrasFromInterface = bundle;
    }

    @Override
    protected void onBindViewHolder(final MessagesHolder holder, int position, Messages model) {
        final int newContainerId = getUniqueId();

        holder.textViewMessages.setText(model.getMessages());
        holder.textViewUser.setText(model.getUser());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm a");
        String uncovertedTimeStamp = (model.getTimePosted().toDate().toString());
        String ConvertedTimeStamp = simpleDateFormat.format(new Date(uncovertedTimeStamp));
        holder.timeStamp.setText(ConvertedTimeStamp);
        holder.bAdd.setVisibility(View.GONE);
        holder.container.setVisibility(View.GONE);



        //Compare size and add button at buttom of view
        if(position==getItemCount()-1){
            holder.bAdd.setVisibility(View.VISIBLE);
        }
        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bAdd.setVisibility(View.GONE);
                holder.container.setId(newContainerId);
                holder.container.setVisibility(View.VISIBLE);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Fragment replyFrag = new AddReplyFragment();
                Bundle extras = new Bundle();
                extras.putString("USER","Test_User");
                replyFrag.setArguments(extras);
                replyFrag.setArguments(extrasFromInterface);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(newContainerId,replyFrag,"first").addToBackStack(null).commit();
            }
        });
        holder.container.setVisibility(View.VISIBLE);
    }

    public int getUniqueId(){
        return (int) SystemClock.currentThreadTimeMillis();
    }

    @NonNull
    @Override
    public MessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforuminterface,parent,false);
        EmojiCompat.init(new BundledEmojiCompatConfig(view.getContext()));
        context = this;
        return new MessagesHolder(view);
    }

    class MessagesHolder extends RecyclerView.ViewHolder{
        EmojiTextView textViewMessages;
        EmojiTextView textViewUser;
        TextView timeStamp;
        Button bAdd;

        FrameLayout container;
        public MessagesHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessages = itemView.findViewById(R.id.tvDescription_reply);
            textViewUser = itemView.findViewById(R.id.tvUsername);
            timeStamp = itemView.findViewById(R.id.tvTimestampMessages);
            bAdd = itemView.findViewById(R.id.bAdd);
            container = itemView.findViewById(R.id.replyFragment);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}

