package my.edu.fsktm.um.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowProfile extends AppCompatActivity {
    FirebaseAuth mAuth ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();


        TextView tvEmail = (TextView)findViewById(R.id.tvShowEmail);
        ImageView image = (ImageView)findViewById(R.id.ivShowProfile);

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

        tvEmail.setText(email);

    }
}
