package my.edu.fsktm.um.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import my.edu.fsktm.um.finalproject.LoginPage.EmailPassActivity;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    RelativeLayout mylayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmojiCompat.init(new BundledEmojiCompatConfig(MainActivity.this));

        getSupportActionBar().hide();

        mylayout = (RelativeLayout) findViewById(R.id.my_bg_layout);
        animationDrawable = (AnimationDrawable) mylayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
    }

    // HELLO WORLD FROM KHALISH

    public void goToLoginPage(View v) {
        Intent intent = new Intent(this, EmailPassActivity.class);
        startActivity(intent);
    }
}
