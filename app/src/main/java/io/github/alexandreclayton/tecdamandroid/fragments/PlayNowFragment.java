package io.github.alexandreclayton.tecdamandroid.fragments;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.github.alexandreclayton.tecdamandroid.API.SpotifyImpl;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.CurrentPlaying;
import io.github.alexandreclayton.tecdamandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.github.alexandreclayton.tecdamandroid.MainActivity.TOKEN;

public class PlayNowFragment extends Fragment implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    private SpotifyImpl spotify;
    private Player mPlayer;
    private SharedPreferences sharedPref;
    private String playlist;
    // Componentes de tela
    private ImageButton btnPlayPause;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private TextView txtMusica;
    private TextView txtAlbum;
    private ImageView imgPlaylist;
    private NotificationCompat.Builder builderNotification;


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
        txtMusica = root.findViewById(R.id.txtMusica);
        txtAlbum = root.findViewById(R.id.txtAlbum);
        imgPlaylist = root.findViewById(R.id.imgPlaylist);

        // Pegando a playlist selecionada.
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        playlist = (String) sharedPref.getString("PLAYLIST", new String());

        // Configurando API Spotify
        if (MainActivity.TOKEN != null) {
            spotify = new SpotifyImpl(MainActivity.TOKEN);
            getDataCurrentPlaying();
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
                            getDataCurrentPlaying();
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
                            getDataCurrentPlaying();
                        }

                        @Override
                        public void onError(Error error) {

                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayer != null && mPlayer.getPlaybackState().isPlaying) {
            btnPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            btnPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoggedIn() {
        // if (!playlist.isEmpty()) {
        //     mPlayer.playUri(null, playlist, 0, 0);
        // }
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
            case UNKNOWN:
                break;
            case kSpPlaybackNotifyPlay:
                break;
            case kSpPlaybackNotifyPause:
                break;
            case kSpPlaybackNotifyTrackChanged:
                break;
            case kSpPlaybackNotifyNext:
                break;
            case kSpPlaybackNotifyPrev:
                break;
            case kSpPlaybackNotifyShuffleOn:
                break;
            case kSpPlaybackNotifyShuffleOff:
                break;
            case kSpPlaybackNotifyRepeatOn:
                break;
            case kSpPlaybackNotifyRepeatOff:
                break;
            case kSpPlaybackNotifyBecameActive:
                break;
            case kSpPlaybackNotifyBecameInactive:
                break;
            case kSpPlaybackNotifyLostPermission:
                break;
            case kSpPlaybackEventAudioFlush:
                break;
            case kSpPlaybackNotifyAudioDeliveryDone:
                break;
            case kSpPlaybackNotifyContextChanged:
                break;
            case kSpPlaybackNotifyTrackDelivered:
                break;
            case kSpPlaybackNotifyMetadataChanged:
                getDataCurrentPlaying();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {

    }

    public void getDataCurrentPlaying() {
        if (spotify != null) {
            spotify.getSpotifyService().getCurrentPlaying().enqueue(new Callback<CurrentPlaying>() {
                @Override
                public void onResponse(Call<CurrentPlaying> call, Response<CurrentPlaying> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CurrentPlaying currentPlaying = response.body();

                        txtAlbum.setText(currentPlaying.item.album.name);
                        txtMusica.setText(currentPlaying.item.name);
                        Picasso.with(getContext()).load(currentPlaying.item.album.images.get(0).url).into(imgPlaylist);
                        // Update notification
                        try {

                            setNotification(currentPlaying.item.album.images.get(0).url
                                    , currentPlaying.item.name, !currentPlaying.item.artists.isEmpty() ?
                                            currentPlaying.item.artists.get(0).name : currentPlaying.item.album.name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CurrentPlaying> call, Throwable t) {

                }
            });
        }
    }

    public void setNotification(String image, String titulo, String text) {

        // Notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_play_circle_outline_black_24dp)
                        .setContentTitle(titulo)
                        .setContentText(text);
        NotificationManager mNotifyMgr =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, mBuilder.build());

    }
}
