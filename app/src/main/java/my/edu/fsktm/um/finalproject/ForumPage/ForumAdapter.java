package my.edu.fsktm.um.finalproject.ForumPage;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

import my.edu.fsktm.um.finalproject.R;

public class ForumAdapter extends FirestoreRecyclerAdapter<Forum,ForumAdapter.ForumHolder> {

    private OnItemClickListener listener;

    public ForumAdapter(FirestoreRecyclerOptions<Forum> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(ForumHolder forumHolder, int i, Forum forum) {
        forumHolder.textViewTitle.setText(forum.getTitle());
        forumHolder.textViewDescription.setText(forum.getDescription());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm a");
        String uncovertedTimeStamp = (forum.getDatePosted().toDate().toString());
        String ConvertedTimeStamp = simpleDateFormat.format(new Date(uncovertedTimeStamp));
        forumHolder.timeStamp.setText(ConvertedTimeStamp);

    }

    @NonNull
    @Override
    public ForumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforumtitle,parent,false);
        EmojiCompat.init(new BundledEmojiCompatConfig(v.getContext()));
        return new ForumHolder(v);

    }
    class ForumHolder extends RecyclerView.ViewHolder{

        EmojiTextView textViewTitle;
        EmojiTextView textViewDescription;
        TextView timeStamp;
        ImageView ivPicture;

        public ForumHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewDescription = itemView.findViewById(R.id.tvDescription);
            timeStamp = itemView.findViewById(R.id.tvTimestamp);

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // NO_POSITION to prevent app crash when click -1 index
                    if(position != RecyclerView.NO_POSITION && listener !=null ){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}

