package my.edu.fsktm.um.finalproject.ForumTitle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import my.edu.fsktm.um.finalproject.R;

public class ForumInterface extends AppCompatActivity {
    private FirebaseFirestore db;
    private CollectionReference forumInformation;
    private CollectionReference forumMessages;
    private MessagesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_interface);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String forum_title = extras.getString("TITLE");
        String forum_type = extras.getString("FORUM_TYPE");
        String forum_id = extras.getString("FORUM_ID");
        String forum_description = extras.getString("DESCRIPTION");
        String forum_username = extras.getString("USER");

        db = FirebaseFirestore.getInstance();
        forumInformation = db.collection(forum_type).document(forum_id).collection(forum_title);
        forumMessages = db.collection(forum_type).document(forum_id).collection("messages");

        TextView description = (TextView)findViewById(R.id.tvForumDesc);
        TextView test = (TextView)findViewById(R.id.tvForumTitle);
        TextView username = (TextView)findViewById(R.id.tvForumUser);

        test.setText(forum_title);
        description.setText(forum_description);
        username.setText(forum_username);

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = forumMessages.orderBy("timePosted",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Messages> options = new FirestoreRecyclerOptions.Builder<Messages>()
                .setQuery(query,Messages.class)
                .build();

        adapter = new MessagesAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
