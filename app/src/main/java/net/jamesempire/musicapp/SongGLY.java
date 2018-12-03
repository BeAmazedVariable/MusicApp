package net.jamesempire.musicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class SongGLY extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    //Declare the variable we need to use
    public ImageView imgDisc;
    private MediaPlayer song;
    private SeekBar seekBar;
    private ImageButton play;
    private ImageButton repeat, shuffle;
    private TextView elapseTimeLabel, remainTimeLabel;
    int lengthSong;
    final int SHUFFLE_CHECKED = 1;
    final int SHUFFLE_UNCHECKED = 0;
    final int REPEAT_CHECKED = 1;
    final int REPEAT_UNCHECKED = 0;
    int loopingState = REPEAT_UNCHECKED;
    int shuffleState = SHUFFLE_UNCHECKED;
    private Handler checkSeekQueue, checkTimeLabelQueue;  //A handler is and thread manager which queue the thread in the background (organize purpose)
    Runnable checkSeek, checkTimeLabel;        //Create a new thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_gly);

        //Define the variable with appropriate component on the layout for the song
        imgDisc = findViewById(R.id.rotateDisc);
        seekBar = findViewById(R.id.seekBar);
        play = findViewById(R.id.btnPlay);
        ImageButton next = findViewById(R.id.btnNext);
        ImageButton previous = findViewById(R.id.btnPrevious);
        repeat = findViewById(R.id.btnRepeat);
        shuffle = findViewById(R.id.btnShuffle);
        elapseTimeLabel = findViewById(R.id.elapseTime);
        remainTimeLabel = findViewById(R.id.remainTime);

        //Create a new handlers to manage the threads
        checkSeekQueue = new Handler();
        checkTimeLabelQueue = new Handler();

        //Group the image button for the song into one group to declare OnClick()
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        repeat.setOnClickListener(this);
        shuffle.setOnClickListener(this);

        //Group the image button for the song into one group to declare OnLongClick()
        next.setOnLongClickListener(this);
        previous.setOnLongClickListener(this);


        //Define the song
        song = MediaPlayer.create(getApplicationContext(), R.raw.gly);

        //Get the duration of the song
        lengthSong = song.getDuration();

        //Set the media and seek bar to be ready for playing purpose
        song.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set the length of the seek bar match the length of the song
                seekBar.setMax(lengthSong);

                //Start the thread to update seek bar and time label
                changeSeekBar(seekBar, song);
                changeTime();

            }
        });

        //Move to a specific time of the song through the user input
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Seek to the position follow by follow the user input
                if (fromUser) {
                    song.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Action which will take place by the end of the playback
        song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextSong();
            }
        });
    }

    //A thread to change the seek bar to the current position
    private void changeSeekBar(final SeekBar seekBar, final MediaPlayer music) {
        //Set the seek bar progress to the current position
        seekBar.setProgress(music.getCurrentPosition());

        //Create a loop that call the thread every 1 sec to check for the seek bar
        if (music.isPlaying()) {
            checkSeek = new Runnable() {
                @Override
                public void run() {
                    changeSeekBar(seekBar, music);
                }
            };
            checkSeekQueue.postDelayed(checkSeek, 1000);
        }
    }

    //Function to keep track at the elapse time and remaining time
    private void changeTime() {
        //Send the time to createTimeLabel so as to process the output follow a formal
        String elapsedTime = createTimeLabel(song.getCurrentPosition());
        elapseTimeLabel.setText(elapsedTime);
        String remainingTime = createTimeLabel(lengthSong - song.getCurrentPosition());
        remainTimeLabel.setText("- " + remainingTime);
        //Create a loop that call the thread every 1 sec to check for the time label
        if (song.isPlaying()) {
            checkTimeLabel = new Runnable() {
                @Override
                public void run() {
                    changeTime();
                }
            };
            checkTimeLabelQueue.postDelayed(checkTimeLabel, 1000);
        }
    }

    //Function to format the time label
    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) {
            timeLabel += "0";

        }

        timeLabel += sec;
        return timeLabel;
    }

    //On click method to implement each button in the layout
    @Override
    public void onClick(View v) {
        //Declare an instance for the animation class
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        switch (v.getId()) {
            case R.id.btnPlay:
                //Pause the music
                if (song.isPlaying()) {
                    song.pause();

                    //Stop the disc from rotating
                    imgDisc.clearAnimation();

                    //Set the play button
                    play.setImageResource(R.drawable.play_button);

                }
                //Start the music
                else {
                    song.start();
                    //Start to rotate the disc
                    imgDisc.startAnimation(rotate);

                    //Start the thread to update seek bar and time label
                    changeSeekBar(seekBar, song);
                    changeTime();

                    //Change to the pause image
                    play.setImageResource(R.drawable.pause_button);
                }
                break;
            case R.id.btnNext:
                //Reset the song
                song.reset();

                if (shuffleState == SHUFFLE_CHECKED) {
                    songsShuffle();
                } else {
                    nextSong();
                }
                break;
            case R.id.btnPrevious:
                //Reset the song
                song.reset();

                if (shuffleState == SHUFFLE_CHECKED) {
                    songsShuffle();
                } else {
                    previousSong();
                }
                break;
            case R.id.btnRepeat:
                //Decide to looping this song or not
                if (loopingState == REPEAT_CHECKED) {
                    song.setLooping(false);
                    loopingState = REPEAT_UNCHECKED;
                    repeat.setImageResource(R.drawable.repeat_button);
                } else {
                    song.setLooping(true);
                    loopingState = REPEAT_CHECKED;
                    repeat.setImageResource(R.drawable.clicked_repeat_button);
                }
                break;
            case R.id.btnShuffle:
                //Decide if we shuffle the songs or not
                if (shuffleState == SHUFFLE_CHECKED) {

                    shuffleState = SHUFFLE_UNCHECKED;
                    shuffle.setImageResource(R.drawable.shuffle_button);
                } else {
                    shuffleState = SHUFFLE_CHECKED;
                    shuffle.setImageResource(R.drawable.clicked_shuffle_button);
                }
                break;
        }
    }

    //Fast forward 5 secs or move back 5 secs when the user hold the button
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                //Fast forward 5 secs
                song.seekTo(song.getCurrentPosition() + 5000);
                break;
            case R.id.btnPrevious:
                //Move back 5 secs
                song.seekTo(song.getCurrentPosition() - 5000);
                break;
        }
        return true;
    }

    //Shuffle the songs if the shuffle button is clicked
    public void songsShuffle() {
        Intent shuffledSong = new Intent();
        Random songNumber = new Random();
        int nextSong = songNumber.nextInt(3);
        switch (nextSong) {
            case 0:
                shuffledSong.setClass(getApplicationContext(), SongSOY.class);
                break;
            case 1:
                shuffledSong.setClass(getApplicationContext(), SongADCG.class);
                break;
            case 2:
                shuffledSong.setClass(getApplicationContext(), SongC.class);
                break;
        }
        sendData(shuffledSong);
        startActivity(shuffledSong);
    }

    //Send the data to the next song
    public void sendData(Intent nextSong) {
        nextSong.putExtra("LoopingState", loopingState);
        nextSong.putExtra("ShuffleState", shuffleState);
    }

    //Retrieve data from previous song
    public void retrieveData() {
        Intent retrieveResources = getIntent();
        loopingState = retrieveResources.getIntExtra("LoopingState", 0);
        shuffleState = retrieveResources.getIntExtra("ShuffleState", 0);

        if (loopingState == REPEAT_CHECKED) {
            song.setLooping(true);
            repeat.setImageResource(R.drawable.clicked_repeat_button);
        } else {
            song.setLooping(false);
            repeat.setImageResource(R.drawable.repeat_button);
        }

        if (shuffleState == SHUFFLE_CHECKED) {
            shuffle.setImageResource(R.drawable.clicked_shuffle_button);
        } else {
            shuffle.setImageResource(R.drawable.shuffle_button);
        }
    }

    //Move to the next song
    public void nextSong() {
        //Start the next song in the list
        Intent nextSong = new Intent(getApplicationContext(), SongSOY.class);
        sendData(nextSong);
        startActivity(nextSong);
    }

    //Move to the previous song
    public void previousSong() {
        //Start the previous song in the list
        Intent previousSong = new Intent(getApplicationContext(), SongC.class);
        sendData(previousSong);
        startActivity(previousSong);
    }
}

