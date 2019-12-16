package my.edu.fsktm.um.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import my.edu.fsktm.um.finalproject.ForumPage.ForumPage;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainMenuActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    RelativeLayout mylayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().hide();

        mylayout2 = (RelativeLayout) findViewById(R.id.mainMenuLayout);
        animationDrawable = (AnimationDrawable) mylayout2.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

    }


    public void goToForumPage(View view) {
        Intent intent = new Intent(this, ForumPage.class);
        startActivity(intent);
    }
}
