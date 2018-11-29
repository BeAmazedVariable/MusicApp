package net.jamesempire.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SongListing extends AppCompatActivity {

    ListView listSong;
    String[] songs = {"Girls like you", "Shape of you", "Anh đếch cần gì nhiều ngoài em","Closer"};
    String[] artists = {"Maroon 5 ft. Cardi B", "Ed Sheeran", "Đen ft. Vũ., Thành Đồng","The Chainsmokers ft. Halsey"};
    Integer[] albumCover = {R.drawable.cover_gly,R.drawable.cover_soy,R.drawable.cover_adcg,R.drawable.cover_c};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_listing);

        listSong = findViewById(R.id.songList);
        CustomListView myListView = new CustomListView(this,songs,albumCover,artists);
        listSong.setAdapter(myListView);
        listSong.setDividerHeight(20);
        listSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        startActivity(new Intent(SongListing.this, SongGLY.class));
                        break;
                    case 1:
                        startActivity(new Intent(SongListing.this, SongSOY.class));
                        break;
                    case 2:
                        startActivity(new Intent(SongListing.this, SongADCG.class));
                        break;
                    case 3:
                        startActivity(new Intent(SongListing.this, SongC.class));
                        break;
                }
            }
        });
    }


}
