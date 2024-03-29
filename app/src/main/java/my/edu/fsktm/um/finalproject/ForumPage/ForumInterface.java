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

    // Create Firebase data type to make use of it
    private FirebaseFirestore db;
    private CollectionReference forumInformation;
    private CollectionReference forumMessages;
    private MessagesAdapter adapter;
    private FirebaseAuth mAuth;

    // Create a string
    public String forum_email;
    public String forum_title;
    public String forum_type;
    public String forum_id;
    public String forum_description;
    public String forum_time_posted;
    public String realEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_interface);

        // To allow emoji used in the interface
        EmojiCompat.init(new BundledEmojiCompatConfig(ForumInterface.this));

        // Receiving all the data from previous class in java
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        forum_email = extras.getString("EMAIL");
        forum_title = extras.getString("TITLE");
        forum_type = extras.getString("FORUM_TYPE");
        forum_id = extras.getString("FORUM_ID");
        forum_description = extras.getString("DESCRIPTION");
        forum_time_posted = extras.getString("TIME_POSTED");

        // Create a string to hold the value of user id
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();

        // Get the data of forum that user select from previous activity
        forumInformation = db.collection(forum_type).document(forum_id).collection(forum_title);

        // Get the data of messages in that forum
        forumMessages = db.collection(forum_type).document(forum_id).collection("messages");

        //  Assigning
        TextView description = (TextView)findViewById(R.id.tvForumDesc);
        TextView test = (TextView)findViewById(R.id.tvForumTitle);
        TextView username = (TextView)findViewById(R.id.tvForumUser);
        TextView timePosted = (TextView)findViewById(R.id.tvTimePosted);
        ImageButton ibDelete = (ImageButton)findViewById(R.id.ibDelete);

        // Set the information received from previous class to the text view in the layout
        test.setText(forum_title);
        description.setText(forum_description);
        username.setText(forum_email);
        timePosted.setText(forum_time_posted);

        // Make a function to get email of current user from the cloud firestore
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

        // Make a delete function
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

    public String getForum_type() {
        return forum_type;
    }

    public String getForum_id() {
        return forum_id;
    }

    // Setting up the recyclerview
    private void setUpRecyclerView(Bundle bundle){
        Query query = forumMessages.orderBy("timePosted",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Messages> options = new FirestoreRecyclerOptions.Builder<Messages>()
                .setQuery(query,Messages.class)
                .build();

        adapter = new MessagesAdapter(this,options,bundle,forum_type,forum_id);

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
