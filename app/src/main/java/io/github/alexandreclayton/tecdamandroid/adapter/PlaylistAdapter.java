package io.github.alexandreclayton.tecdamandroid.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.alexandreclayton.tecdamandroid.Model.PlaylistBase;
import io.github.alexandreclayton.tecdamandroid.R;

/**
 * Created by alexandresette on 07/10/17.
 */

public class PlaylistAdapter extends ArrayAdapter<PlaylistBase> {

    private int resource;

    public PlaylistAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.resource = resource;
    }

    public PlaylistAdapter(@NonNull Context context, @LayoutRes int resource, List<PlaylistBase> list) {
        super(context, resource, list);
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.playlistview, null);
        }

        PlaylistBase playlistBase = getItem(position);

        if (playlistBase != null) {
            TextView txt1 = (TextView) v.findViewById(R.id.label);

            if (playlistBase.name != null) {
                txt1.setText(playlistBase.name);
            }
        }
        return v;
    }
}
