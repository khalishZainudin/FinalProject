package my.edu.fsktm.um.finalproject.ForumPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import my.edu.fsktm.um.finalproject.ForumPage.Fragment.HardwareFragment;
import my.edu.fsktm.um.finalproject.ForumPage.Fragment.PictureFragment;
import my.edu.fsktm.um.finalproject.ForumPage.Fragment.ReviewFragment;
import my.edu.fsktm.um.finalproject.ForumPage.Fragment.SalesFragment;
import my.edu.fsktm.um.finalproject.ForumPage.Fragment.TechnicalSupportFragment;
import my.edu.fsktm.um.finalproject.R;

public class ForumPage extends AppCompatActivity {
    // Declare database of Cloud Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Declare authentication of Firebase
    private FirebaseAuth mAuth;

    // Declare email
    String email;

    // Declare context of ForumPage.java
    ForumPage context;

    // Declare RecyclerView of ForumPage.java
    RecyclerView background;

    // Declare of image button
    ImageButton IVReview,IVTechnical,IVHardware,IVSales,IVPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_title);

        getSupportActionBar().hide();

        // Create a string to hold the value of current user id
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();

        // Create a floating action button and identifying it with the id
        FloatingActionButton fabadd = (FloatingActionButton) findViewById(R.id.fabAdd);

        // Identifying the background with the id
        background = (RecyclerView)findViewById(R.id.my_recycler_view);

        // Don't show the background before the user click any type
        background.setVisibility(View.GONE);

        context = this;

        // Turn the background into visible and show the fragment of user's choice
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = null;
                if (v == findViewById(R.id.iBReview)){
                    background.setVisibility(View.VISIBLE);
                    selectedFragment = new ReviewFragment();
                }
                else if (v == findViewById(R.id.iBTech)){
                    background.setVisibility(View.VISIBLE);
                    selectedFragment = new TechnicalSupportFragment();
                }
                else if (v == findViewById(R.id.iBHardware)){
                    background.setVisibility(View.VISIBLE);
                    selectedFragment = new HardwareFragment();
                }
                else if (v == findViewById(R.id.iBSales)){
                    background.setVisibility(View.VISIBLE);
                    selectedFragment = new SalesFragment();
                }
                else if (v == findViewById(R.id.iBPictures)){
                    background.setVisibility(View.VISIBLE);
                    selectedFragment = new PictureFragment();
                }

                // Transaction of fragment
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,selectedFragment);
                transaction.commit();
            }

        };

        // Assigning
        IVReview = (ImageButton)findViewById(R.id.iBReview);
        IVTechnical = (ImageButton)findViewById(R.id.iBTech);
        IVHardware = (ImageButton)findViewById(R.id.iBHardware);
        IVSales = (ImageButton)findViewById(R.id.iBSales);
        IVPictures = (ImageButton)findViewById(R.id.iBPictures);

        // Give each Image View a listener
        IVReview.setOnClickListener(listener);
        IVTechnical.setOnClickListener(listener);
        IVHardware.setOnClickListener(listener);
        IVSales.setOnClickListener(listener);
        IVPictures.setOnClickListener(listener);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumPage.this,AddForum.class);
                startActivity(intent);
            }
        });
    }

}

