package my.edu.fsktm.um.finalproject.ForumTitle;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Date;

import my.edu.fsktm.um.finalproject.R;

public class ForumAdapter extends FirestoreRecyclerAdapter<Forum,ForumAdapter.ForumHolder> {

    public ForumAdapter(FirestoreRecyclerOptions<Forum> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(ForumHolder forumHolder, int i, Forum forum) {
        forumHolder.textViewTitle.setText(forum.getTitle());
        forumHolder.textViewDescription.setText(forum.getDescription());
        forumHolder.timeStamp.setText(forum.getDatePosted().toString());
    }

    @NonNull
    @Override
    public ForumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforumtitle,parent,false);
        return new ForumHolder(v);
    }
    class ForumHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;
        TextView timeStamp;
        public ForumHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.description);
            timeStamp = itemView.findViewById(R.id.timestamp);
        }
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}

