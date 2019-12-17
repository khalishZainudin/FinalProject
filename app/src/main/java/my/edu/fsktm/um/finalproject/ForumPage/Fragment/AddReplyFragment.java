package my.edu.fsktm.um.finalproject.ForumPage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import my.edu.fsktm.um.finalproject.ForumPage.ForumInterface;
import my.edu.fsktm.um.finalproject.R;

public class AddReplyFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bAddReply;
    Button bCancelReply;
    EditText etReply;
    Timestamp now = Timestamp.now();
    String userReply;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_reply_fragment,container,false);
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();

        final String forum_title = getArguments().getString("TITLE");
        final String forum_type = getArguments().getString("FORUM_TYPE");
        final String forum_id = getArguments().getString("FORUM_ID");

        etReply = (EditText)view.findViewById(R.id.etReply);
        bAddReply = (Button)view.findViewById(R.id.bSubmitReply);
        bCancelReply = (Button)view.findViewById(R.id.bCancel);

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
                db.collection(forum_type).document(forum_id).collection("messages").add(reply);

                //Remove current fragment
                Fragment f = getFragmentManager().findFragmentByTag("first");
                if(f!=null) getFragmentManager().beginTransaction().remove(f).commit();
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
}
