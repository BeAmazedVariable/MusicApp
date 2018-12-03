package net.jamesempire.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);
        ImageView slogan = findViewById(R.id.slogan);
        View bar = findViewById(R.id.bar);

        //Set animation for the logo
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        logo.setAnimation(fade);
        //Set animation for the slogan
        Animation pushLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_left);
        slogan.setAnimation(pushLeft);
        bar.setAnimation(pushLeft);
        //Set the end time to move to another activity
        TimerTask splashScreen = new TimerTask()
        {
            @Override
            public void run()
            {
                finish();
                startActivity(new Intent(SplashScreen.this, SongListing.class));
            }
        };
        Timer splashScreenTime =new Timer();
        splashScreenTime.schedule(splashScreen,5000);
    }
}
