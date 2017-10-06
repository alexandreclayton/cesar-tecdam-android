package io.github.alexandreclayton.tecdamandroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.github.alexandreclayton.tecdamandroid.API.SpotifyImpl;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.Playlist;
import io.github.alexandreclayton.tecdamandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListFragment extends Fragment {

    private ListView lvPlaylist;
    private SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvPlaylist = root.findViewById(R.id.lvPlaylist);

        if (!MainActivity.TOKEN.isEmpty()) {
            SpotifyImpl spotify = new SpotifyImpl(MainActivity.TOKEN);
            spotify.getSpotifyService().getPlaylists().enqueue(new Callback<Playlist>() {
                @Override
                public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                    if (response.isSuccessful()) {
                        Playlist playlist = response.body();
                        //txtQtdPlaylist.setText(playlist.total.toString());

                    }
                }

                @Override
                public void onFailure(Call<Playlist> call, Throwable t) {
                    Log.e("QUERY", t.getMessage(), t);
                }
            });
        }

        return root;
    }
}
