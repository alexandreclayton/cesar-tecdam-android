package io.github.alexandreclayton.tecdamandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

import io.github.alexandreclayton.tecdamandroid.API.SpotifyImpl;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.R;

import static io.github.alexandreclayton.tecdamandroid.MainActivity.TOKEN;

public class PlayNowFragment extends Fragment implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    private SpotifyImpl spotify;
    private Player mPlayer;
    private SharedPreferences sharedPref;
    //private ArraySet<String> playlist;
    private String playlist;
    // Componentes de tela
    private ImageButton btnPlayPause;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private ImageButton btnVolDown;
    private ImageButton btnVolUp;
    private TextView txtMusica;
    private TextView txtAlbum;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playnow, container, false);
        // Componentes de tela
        btnPlayPause = root.findViewById(R.id.btnPlayPause);
        btnPrevious = root.findViewById(R.id.btnPrevious);
        btnNext = root.findViewById(R.id.btnNext);
        btnVolDown = root.findViewById(R.id.btnVolDown);
        btnVolUp = root.findViewById(R.id.btnVolUp);
        txtMusica = root.findViewById(R.id.txtMusica);
        txtAlbum = root.findViewById(R.id.txtAlbum);

        // Pegando a playlist selecionada.
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        playlist = (String) sharedPref.getString("PLAYLIST", new String());

        // Configurando API Spotify
        if (MainActivity.TOKEN != null) {
            spotify = new SpotifyImpl(MainActivity.TOKEN);
        }

        // Configurando o play
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
        // Eventos
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playlist.isEmpty()) {
                    if (mPlayer.getPlaybackState().isPlaying) {
                        mPlayer.pause(new Player.OperationCallback() {
                            @Override
                            public void onSuccess() {
                                btnPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                            }

                            @Override
                            public void onError(Error error) {

                            }
                        });
                    } else {
                        mPlayer.playUri(null, playlist, 0, 0);
                        btnPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    }
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playlist.isEmpty()) {
                    mPlayer.skipToNext(new Player.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            // Notification
                        }

                        @Override
                        public void onError(Error error) {

                        }
                    });
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playlist.isEmpty()) {
                    mPlayer.skipToPrevious(new Player.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            // Notification
                        }

                        @Override
                        public void onError(Error error) {

                        }
                    });
                }
            }
        });
        btnVolUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spotify != null) {
                    spotify.getSpotifyService().ajustVolume(80);
                }
            }
        });
        btnVolDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spotify != null) {
                    spotify.getSpotifyService().ajustVolume(20);
                }
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
        if (!playlist.isEmpty()) {
            mPlayer.playUri(null, playlist, 0, 0);
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
