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
        // Get the current forum information and set it to the text view in layout
        forumHolder.textViewTitle.setText(forum.getTitle());
        forumHolder.textViewDescription.setText(forum.getDescription());

        // Create date format for easy to read firebase timestamp
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm a");
        String uncovertedTimeStamp = (forum.getDatePosted().toDate().toString());
        String ConvertedTimeStamp = simpleDateFormat.format(new Date(uncovertedTimeStamp));

        // Set the timestamp in layout the we retrieve from firebase
        forumHolder.timeStamp.setText(ConvertedTimeStamp);
    }

    @NonNull
    @Override
    public ForumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the view in adapter
        android.view.View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforumtitle,parent,false);

        // To enable Emoji in context
        EmojiCompat.init(new BundledEmojiCompatConfig(v.getContext()));
        return new ForumHolder(v);

    }
    class ForumHolder extends RecyclerView.ViewHolder{

        // Declare the Palette
        TextView textViewTitle;
        TextView textViewDescription;
        TextView timeStamp;

        public ForumHolder(View itemView) {
            super(itemView);

            // Assign declaration of Palette to the layout
            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewDescription = itemView.findViewById(R.id.tvDescription);
            timeStamp = itemView.findViewById(R.id.tvTimestamp);

            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    // NO_POSITION to prevent app crash when click -1 index
                    if(position != RecyclerView.NO_POSITION && listener !=null ){

                        // Get the item position in recycler view
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

