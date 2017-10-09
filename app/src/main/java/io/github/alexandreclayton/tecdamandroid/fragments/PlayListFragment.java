package io.github.alexandreclayton.tecdamandroid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import io.github.alexandreclayton.tecdamandroid.API.SpotifyImpl;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.Pager;
import io.github.alexandreclayton.tecdamandroid.Model.Playlist;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistBase;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistTrack;
import io.github.alexandreclayton.tecdamandroid.R;
import io.github.alexandreclayton.tecdamandroid.adapter.PlaylistAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListFragment extends Fragment {

    private ListView lvPlaylist;
    private SimpleCursorAdapter mAdapter;
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        lvPlaylist = root.findViewById(R.id.lvPlaylist);

        if (!MainActivity.TOKEN.isEmpty()) {
            final SpotifyImpl spotify = new SpotifyImpl(MainActivity.TOKEN);
            spotify.getSpotifyService().getPlaylists().enqueue(new Callback<Playlist>() {
                @Override
                public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                    if (response.isSuccessful()) {
                        Playlist playlist = response.body();

                        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext()
                                , R.layout.playlistview, playlist.items);
                        lvPlaylist.setAdapter(playlistAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Playlist> call, Throwable t) {
                    Log.e("QUERY", t.getMessage(), t);
                }
            });

            lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PlaylistBase playlistBase = (PlaylistBase) adapterView.getItemAtPosition(i);

                    spotify.getSpotifyService().getPlaylistTraks(playlistBase.owner.id, playlistBase.id).enqueue(new Callback<Pager<PlaylistTrack>>() {
                        @Override
                        public void onResponse(Call<Pager<PlaylistTrack>> call, Response<Pager<PlaylistTrack>> response) {
                            //Set<String> playlists = new ArraySet<String>();
                            //for(PlaylistTrack item : response.body().items){
                            //    playlists.add(item.track.uri);
                            //}
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("PLAYLIST", playlistBase.uri);
                            editor.commit();
                        }

                        @Override
                        public void onFailure(Call<Pager<PlaylistTrack>> call, Throwable t) {

                        }
                    });
                    Log.d("ItemClicked", playlistBase.id);
                }
            });
        }

        return root;
    }
}
