package net.jamesempire.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends AppCompatActivity {

    private ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        loadingBar =findViewById(R.id.loadingBar);

        //Set the time to end the activity using the timer
        TimerTask loadingScreen = new TimerTask()
        {
            @Override
            public void run() {
                loadingProgressBar();
                finish();
                startActivity(new Intent(LoadingScreen.this,SplashScreen.class));
            }
        };
        Timer loadingScreenTime = new Timer();
        loadingScreenTime.schedule(loadingScreen,4000);
    }

    //Let the progress bar to load
    private void loadingProgressBar()
    {
        for (int progress=0; progress < 100; progress += 10) {
            try {
                Thread.sleep(1000);
                loadingBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
