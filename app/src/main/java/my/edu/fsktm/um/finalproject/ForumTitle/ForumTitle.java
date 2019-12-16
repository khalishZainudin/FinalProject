package my.edu.fsktm.um.finalproject.ForumTitle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.firestore.FirebaseFirestore;


import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.HardwareFragment;
import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.PictureFragment;
import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.ReviewFragment;
import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.SalesFragment;
import my.edu.fsktm.um.finalproject.ForumTitle.Fragment.TechnicalSupportFragment;
import my.edu.fsktm.um.finalproject.R;

public class ForumTitle extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ForumTitle context;
    RecyclerView background;
    ImageButton IVReview,IVTechnical,IVHardware,IVSales,IVPictures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_title);
        FloatingActionButton fabadd = (FloatingActionButton) findViewById(R.id.fabAdd);
        background = (RecyclerView)findViewById(R.id.my_recycler_view);
        background.setVisibility(View.GONE);
        context = this;

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
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container,selectedFragment);
                transaction.commit();
            }

        };
        IVReview = (ImageButton)findViewById(R.id.iBReview);
        IVTechnical = (ImageButton)findViewById(R.id.iBTech);
        IVHardware = (ImageButton)findViewById(R.id.iBHardware);
        IVSales = (ImageButton)findViewById(R.id.iBSales);
        IVPictures = (ImageButton)findViewById(R.id.iBPictures);
        IVReview.setOnClickListener(listener);
        IVTechnical.setOnClickListener(listener);
        IVHardware.setOnClickListener(listener);
        IVSales.setOnClickListener(listener);
        IVPictures.setOnClickListener(listener);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumTitle.this,AddForum.class);
                Bundle extras = new Bundle();
                extras.putString("USER","Test_User");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

}

