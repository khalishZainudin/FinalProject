package my.edu.fsktm.um.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import my.edu.fsktm.um.finalproject.Bottleneck.BottleneckActivity;
import my.edu.fsktm.um.finalproject.CompatibilityPage.CompatibleMainActivity;
import my.edu.fsktm.um.finalproject.ForumPage.ForumPage;
import my.edu.fsktm.um.finalproject.LoginPage.UpdateProfileActivity;
import my.edu.fsktm.um.finalproject.PSUCalculatorPage.PSUCalculatorActivity;

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

    public void goToUpdateProfile(View view) {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivity(intent);
    }

    public void goToCompatible(View view) {
        Intent intent = new Intent(this, CompatibleMainActivity.class);
        startActivity(intent);
    }

    public void goToPSUCalculator(View view) {
        Intent intent = new Intent(this, PSUCalculatorActivity.class);
        startActivity(intent);
    }

    public void goToBottleneck(View view) {
        Intent intent = new Intent(this, BottleneckActivity.class);
        startActivity(intent);
    }
}
