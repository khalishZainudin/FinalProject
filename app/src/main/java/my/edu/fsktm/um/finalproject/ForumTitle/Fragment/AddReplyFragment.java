package my.edu.fsktm.um.finalproject.ForumTitle.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import my.edu.fsktm.um.finalproject.R;

public class AddReplyFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button bAddReply;
    EditText etReply;
    Timestamp now = Timestamp.now();
    String userReply;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_reply_fragment,container,false);

        final String username = getArguments().getString("USER");
        final String forum_title = getArguments().getString("TITLE");
        final String forum_type = getArguments().getString("FORUM_TYPE");
        final String forum_id = getArguments().getString("FORUM_ID");

        etReply = (EditText)view.findViewById(R.id.etReply);
        bAddReply = (Button)view.findViewById(R.id.bSubmitReply);

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
                reply.put("user","Ejen Ali");
                db.collection(forum_type).document(forum_id).collection("messages").add(reply);

                //Remove current fragment
                Fragment f = getFragmentManager().findFragmentByTag("first");
                if(f!=null) getFragmentManager().beginTransaction().remove(f).commit();
                //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(container_id).commit();
                //getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.frame)).commit();
            }
        });
        return view;


    }
}
