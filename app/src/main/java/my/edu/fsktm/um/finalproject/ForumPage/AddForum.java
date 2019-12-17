package my.edu.fsktm.um.finalproject.ForumPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.Date;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import my.edu.fsktm.um.finalproject.R;
import my.edu.fsktm.um.finalproject.Upload;

public class AddForum extends AppCompatActivity {
    private Uri mImageUri = null;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference mStorageRef;
    private ArrayList<Uri> image_urls;
    FirebaseAuth mAuth;
    String holderLocation;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner typeSpinner;
    EditText etTitle;
    EditText etDescription;
    EditText etAddPost;
    Button bAdd;
    String doc_id = "";
    String email = "";
    ImageView ivAttach;
    ImageView ivReview;
    TextView tvAttach;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);

        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        typeSpinner = (Spinner)findViewById(R.id.sType);
        etTitle = (EditText)findViewById(R.id.etTitle);
        etDescription =(EditText)findViewById(R.id.etDescription);
        etAddPost = (EditText)findViewById(R.id.etAddPost);
        bAdd = (Button)findViewById(R.id.bAdd);
        ivAttach = (ImageView)findViewById(R.id.ivAttach);
        tvAttach = (TextView)findViewById(R.id.tvAttach);
        ivReview = (ImageView)findViewById(R.id.ivReview);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddForum.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(myAdapter);

        DocumentReference documentReference = db.collection("Users_Profile").document(usern);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){
                        email = documentSnapshot.getString("email");
                    }
                }
            }
        });

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
                forum.put("Email",email);

                //Array for add post
                final Map<String, Object> messages = new HashMap<>();
                messages.put("messages",post);
                messages.put("email",email);
                messages.put("timePosted",now);
                if(mImageUri==null) {
                    messages.put("images", "");
                    db.collection(type).add(forum).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            doc_id = documentReference.getId();
                            db.collection(type).document(doc_id).collection("messages").add(messages);
                            Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddForum.this,ForumPage.class);
                            startActivity(intent);
                        }
                    });
                }

                final ContentResolver cR = getContentResolver();
                if(mImageUri!=null){
                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+ "." + getFileExtenstion(mImageUri));
                    fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    holderLocation = url;
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            messages.put("images",holderLocation);
                                            mProgressBar.setProgress(0);
                                            db.collection(type).add(forum).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    doc_id = documentReference.getId();
                                                    db.collection(type).document(doc_id).collection("messages").add(messages);
                                                    Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(AddForum.this,ForumPage.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    },5000);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddForum.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            // update progress bar with progress
                            double progress = (100 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });
                }
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
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(ivReview);
        }
    }

    private String getFileExtenstion(Uri uri){
        // Get type of an image
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
}
