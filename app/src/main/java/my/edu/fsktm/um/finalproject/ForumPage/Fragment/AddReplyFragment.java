package my.edu.fsktm.um.finalproject.ForumPage.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;

import my.edu.fsktm.um.finalproject.R;

import static android.app.Activity.RESULT_OK;

public class AddReplyFragment extends Fragment {
    private Uri mImageUri = null;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference mStorageRef;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bAddReply;
    Button bCancelReply;
    EditText etReply;
    Timestamp now = Timestamp.now();
    String userReply;
    String email;
    TextView tvAttach;
    ImageView ivAttach;
    Object holderLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_reply_fragment,container,false);
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        final String forum_title = getArguments().getString("TITLE");
        final String forum_type = getArguments().getString("FORUM_TYPE");
        final String forum_id = getArguments().getString("FORUM_ID");

        etReply = (EditText)view.findViewById(R.id.etReply);
        bAddReply = (Button)view.findViewById(R.id.bSubmitReply);
        bCancelReply = (Button)view.findViewById(R.id.bCancel);
        tvAttach = (TextView)view.findViewById(R.id.tvAttach);
        ivAttach = (ImageView) view.findViewById(R.id.ivAttach);

        tvAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

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

        bAddReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReply = etReply.getText().toString();
                if(TextUtils.isEmpty(userReply)) {
                    etReply.setError("Don't let it empty...");
                    return;
                }

                final Map<String, Object> reply = new HashMap<>();
                reply.put("messages",userReply);
                reply.put("timePosted",now);
                reply.put("email",email);
                if(mImageUri==null) {
                    reply.put("images", "");
                    db.collection(forum_type).document(forum_id).collection("messages").add(reply).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Remove current fragment
                            Fragment f = getFragmentManager().findFragmentByTag("first");
                            if(f!=null) getFragmentManager().beginTransaction().remove(f).commit();
                        }
                    });
                }

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
                                            reply.put("images",holderLocation);
                                            db.collection(forum_type).document(forum_id).collection("messages").add(reply).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    //Remove current fragment
                                                    Fragment f = getFragmentManager().findFragmentByTag("first");
                                                    if(f!=null) getFragmentManager().beginTransaction().remove(f).commit();
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
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        bCancelReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getFragmentManager().findFragmentByTag("first");
                if(f!=null) getFragmentManager().beginTransaction().remove(f).commit();
            }
        });

        return view;

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            mImageUri = data.getData();
            tvAttach.setText("File Attached");
        }
    }

    private String getFileExtenstion(Uri uri){
        // Get type of an image
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
