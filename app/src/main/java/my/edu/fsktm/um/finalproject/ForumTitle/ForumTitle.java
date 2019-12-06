package my.edu.fsktm.um.finalproject.ForumTitle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.core.Context;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import my.edu.fsktm.um.finalproject.Fragment.HardwareFragment;
import my.edu.fsktm.um.finalproject.Fragment.ReviewFragment;
import my.edu.fsktm.um.finalproject.Fragment.TechnicalSupportFragment;
import my.edu.fsktm.um.finalproject.R;

public class ForumTitle extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ForumTitle context;

    ImageButton IVReview,IVTechnical,IVHardware;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_title);
        FloatingActionButton fabadd = (FloatingActionButton) findViewById(R.id.fabAdd);

        context = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = null;
                if (v == findViewById(R.id.iBReview)){
                    selectedFragment = new ReviewFragment();
                }
                else if (v == findViewById(R.id.iBTech)){
                    selectedFragment = new TechnicalSupportFragment();
                }
                else if (v == findViewById(R.id.iBHardware)){
                    selectedFragment = new HardwareFragment();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,selectedFragment);
                transaction.commit();
            }

        };
        IVReview = (ImageButton)findViewById(R.id.iBReview);
        IVTechnical = (ImageButton)findViewById(R.id.iBTech);
        IVHardware = (ImageButton)findViewById(R.id.iBHardware);
        IVReview.setOnClickListener(listener);
        IVTechnical.setOnClickListener(listener);
        IVHardware.setOnClickListener(listener);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumTitle.this,AddForum.class);
                startActivity(intent);
            }
        });
    }

}

