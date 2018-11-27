package net.jamesempire.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class SongListing extends AppCompatActivity {

    ListView listSong;
    String[] songs = {"Girls like you", "Shape of you", "Anh đếch cần gì nhiều ngoài em","Closer"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_listing);

        listSong = findViewById(R.id.songList);

        CustomListView myListView = new CustomListView(this,songs);
        listSong.setAdapter(myListView);
        listSong.setDividerHeight(20);
    }
}
