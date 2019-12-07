package my.edu.fsktm.um.finalproject.ForumTitle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.util.HashMap;
import java.util.Map;

import my.edu.fsktm.um.finalproject.R;

public class AddForum extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner typeSpinner;
    EditText etTitle;
    EditText etDescription;
    EditText etAddPost;
    Button bAdd;
    String doc_id = "";
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("USER");

        typeSpinner = (Spinner)findViewById(R.id.sType);
        etTitle = (EditText)findViewById(R.id.etTitle);
        etDescription =(EditText)findViewById(R.id.etDescription);
        etAddPost = (EditText)findViewById(R.id.etAddPost);
        bAdd = (Button)findViewById(R.id.bAdd);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddForum.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(myAdapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String post = etAddPost.getText().toString();
                final String type = typeSpinner.getSelectedItem().toString();

                //Error messages check empty edit text
                if(TextUtils.isEmpty(title)) {
                    etTitle.setError("Where is your title?");
                    return;
                }
                if(TextUtils.isEmpty(description)) {
                    etDescription.setError("Hey! Don't let this box empty!");
                    return;
                }
                if(TextUtils.isEmpty(post)) {
                    etAddPost.setError("Add your long story here");
                    return;
                }

                Timestamp now = Timestamp.now();
                //Array for add new topic
                final Map<String, Object> forum = new HashMap<>();
                forum.put("Title",title);
                forum.put("Description",description);
                forum.put("DatePosted",now);

                //Array for add post
                final Map<String, Object> messages = new HashMap<>();
                messages.put("messages",post);
                messages.put("user",username);
                messages.put("timePosted",now);

                db.collection(type).add(forum).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        doc_id = documentReference.getId();
                        db.collection(type).document(doc_id).collection("messages").add(messages);
                        Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_LONG).show();
                    }
                });

                /*
                //Array for add post
                final Map<String, Object> messages = new HashMap<>();
                messages.put("messages",post);

                db.collection(type).document(doc_id).collection("messages").add(messages).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_SHORT).show();
                    }
                });*/

                Intent intent = new Intent(AddForum.this,ForumTitle.class);
                startActivity(intent);
            }
        });

    }
}
