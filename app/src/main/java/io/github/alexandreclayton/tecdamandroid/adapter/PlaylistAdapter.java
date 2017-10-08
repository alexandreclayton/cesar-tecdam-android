package io.github.alexandreclayton.tecdamandroid.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.alexandreclayton.tecdamandroid.Model.PlaylistBase;
import io.github.alexandreclayton.tecdamandroid.R;

/**
 * Created by alexandresette on 07/10/17.
 */

public class PlaylistAdapter extends ArrayAdapter<PlaylistBase> {

    private int resource = 0;

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
        Resources res = getContext().getResources();

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resource, null);
        }

        PlaylistBase playlistBase = getItem(position);

        if (playlistBase != null) {
            TextView txPlaylistName = (TextView) v.findViewById(R.id.txtPlaylistName);
            TextView txtPlaylAuthor = (TextView) v.findViewById(R.id.txtPlaylistAuthor);
            ImageView imgPlaylist = (ImageView) v.findViewById(R.id.imgPlaylist);

            if (playlistBase.name != null) {
                txPlaylistName.setText(playlistBase.name);
            }

            if (playlistBase.owner.id != null
                    && playlistBase.tracks.total != null) {
                txtPlaylAuthor.setText(playlistBase.tracks.total + " " + res.getString(R.string.music));
                if (playlistBase.images != null && playlistBase.images.size() > 0) {
                    Picasso.with(getContext()).load(playlistBase.images.get(0).url).into(imgPlaylist);
                }
            }
        }
        return v;
    }
}
