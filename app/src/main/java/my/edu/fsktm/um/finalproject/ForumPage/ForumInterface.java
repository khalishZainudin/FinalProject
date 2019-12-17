package my.edu.fsktm.um.finalproject.ForumPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import my.edu.fsktm.um.finalproject.R;

public class ForumInterface extends AppCompatActivity {
    private FirebaseFirestore db;
    private CollectionReference forumInformation;
    private CollectionReference forumMessages;
    private MessagesAdapter adapter;
    private FirebaseAuth mAuth;

    String forum_email;
    String forum_title;
    String forum_type;
    String forum_id;
    String forum_description;
    String forum_time_posted;


    String realEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_interface);

        EmojiCompat.init(new BundledEmojiCompatConfig(ForumInterface.this));
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        forum_email = extras.getString("EMAIL");
        forum_title = extras.getString("TITLE");
        forum_type = extras.getString("FORUM_TYPE");
        forum_id = extras.getString("FORUM_ID");
        forum_description = extras.getString("DESCRIPTION");
        forum_time_posted = extras.getString("TIME_POSTED");


        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();
        forumInformation = db.collection(forum_type).document(forum_id).collection(forum_title);
        forumMessages = db.collection(forum_type).document(forum_id).collection("messages");

        TextView description = (TextView)findViewById(R.id.tvForumDesc);
        TextView test = (TextView)findViewById(R.id.tvForumTitle);
        TextView username = (TextView)findViewById(R.id.tvForumUser);
        TextView timePosted = (TextView)findViewById(R.id.tvTimePosted);
        ImageButton ibDelete = (ImageButton)findViewById(R.id.ibDelete);

        test.setText(forum_title);
        description.setText(forum_description);
        username.setText(forum_email);
        timePosted.setText(forum_time_posted);

        DocumentReference documentReference = db.collection("Users_Profile").document(usern);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){
                        realEmail = documentSnapshot.getString("email");
                    }
                }
            }
        });

        setUpRecyclerView(extras);

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(realEmail.equals(forum_email)){
                     db.collection(forum_type).document(forum_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             Toast.makeText(ForumInterface.this, "Successful Deleted", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(ForumInterface.this,ForumPage.class);
                             startActivity(intent);
                         }
                     });
                 }
            }
        });
    }

    private void setUpRecyclerView(Bundle bundle){
        Query query = forumMessages.orderBy("timePosted",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Messages> options = new FirestoreRecyclerOptions.Builder<Messages>()
                .setQuery(query,Messages.class)
                .build();

        adapter = new MessagesAdapter(this,options,bundle);

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
