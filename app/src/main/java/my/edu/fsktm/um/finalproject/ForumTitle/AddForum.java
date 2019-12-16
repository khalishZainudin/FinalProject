package my.edu.fsktm.um.finalproject.ForumTitle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import my.edu.fsktm.um.finalproject.R;

public class AddForum extends AppCompatActivity {
    private Uri filePath = null;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner typeSpinner;
    EditText etTitle;
    EditText etDescription;
    EditText etAddPost;
    Button bAdd;
    String doc_id = "";
    String username = "";
    ImageView ivAttach;
    TextView tvAttach;
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
        ivAttach = (ImageView)findViewById(R.id.ivAttach);
        tvAttach = (TextView)findViewById(R.id.tvAttach);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddForum.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(myAdapter);

        tvAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


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
                if(filePath!=null) {
                    messages.put("images", filePath.toString());
                }
                db.collection(type).add(forum).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        doc_id = documentReference.getId();
                        db.collection(type).document(doc_id).collection("messages").add(messages);
                        Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_LONG).show();
                    }
                });

                Intent intent = new Intent(AddForum.this,ForumTitle.class);
                startActivity(intent);
            }
        });

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            //filePath = Uri.fromFile(new File(data.getData()));
        }
    }
}
