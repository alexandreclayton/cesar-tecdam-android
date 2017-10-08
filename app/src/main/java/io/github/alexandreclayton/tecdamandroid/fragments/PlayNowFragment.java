package io.github.alexandreclayton.tecdamandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.R;

import static io.github.alexandreclayton.tecdamandroid.MainActivity.TOKEN;

public class PlayNowFragment extends Fragment implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private Player mPlayer;
    private SharedPreferences sharedPref;
    private Set<String> playlist;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playnow, container, false);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        playlist = sharedPref.getStringSet("PLAYLIST", new HashSet<String>());
        Config playerConfig = new Config(this.getContext(), TOKEN, MainActivity.CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                mPlayer = spotifyPlayer;
                mPlayer.addConnectionStateCallback(PlayNowFragment.this);
                mPlayer.addNotificationCallback(PlayNowFragment.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("PlayNowFragment", "Could not initialize player: " + throwable.getMessage());
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("RESUME ======", "PlayNowFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Attach ======", "PlayNowFragment");
    }

    @Override
    public void onDestroy() {
        //Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
        if (!playlist.isEmpty()) {
            Iterator<String> iterator = playlist.iterator();
            if (iterator.hasNext()) {
                String uri = iterator.next();
                mPlayer.playUri(null, uri, 0, 0);
            }
        }
    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }

    }

    @Override
    public void onPlaybackError(Error error) {

    }
}
