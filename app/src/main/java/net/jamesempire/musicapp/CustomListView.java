package net.jamesempire.musicapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//Pass the view that need to be customized
public class CustomListView extends ArrayAdapter<String> {
    private String[] songs;
    private Activity context;
    private Integer[] albumCover;
    private String[] artists;

    public CustomListView(Activity context, String[] songs, Integer[] albumCover, String[] artists) {
        super(context, R.layout.custom_list_view, songs);

        this.context = context;
        this.songs = songs;
        this.albumCover = albumCover;
        this.artists = artists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        //Translate the view from the layout to the .java code
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_list_view, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        //Format the image and the text
        viewHolder.disc.setImageResource(albumCover[position]);
        viewHolder.songsName.setText(songs[position]);
        viewHolder.artistsName.setText(artists[position]);
        return r;
    }

    //Hold the view in the custom_list_view lay out
    class ViewHolder {
        TextView songsName, artistsName;
        ImageView disc;

        ViewHolder(View v) {
            songsName = v.findViewById(R.id.songsName);
            disc = v.findViewById(R.id.recordCover);
            artistsName = v.findViewById(R.id.artistsName);
        }
    }
}
