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

public class CustomListView extends ArrayAdapter<String>
{
    private String[] songs;
    private Activity context;

    public CustomListView(Activity context, String[] songs) {
        super(context, R.layout.custom_list_view,songs);

        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder =null;
        if (r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_list_view,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.disc.setImageResource(R.drawable.disc);
        viewHolder.songsName.setText(songs[position]);


        return r;
    }
    class ViewHolder
    {
        TextView songsName;
        ImageView disc;
        ViewHolder(View v)
        {
            songsName = v.findViewById(R.id.songsName);
            disc = v.findViewById(R.id.disc);
        }
    }
}
