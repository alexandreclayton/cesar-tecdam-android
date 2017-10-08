package io.github.alexandreclayton.tecdamandroid.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.alexandreclayton.tecdamandroid.API.SpotifyImpl;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.FollowedArtists;
import io.github.alexandreclayton.tecdamandroid.Model.Playlist;
import io.github.alexandreclayton.tecdamandroid.Model.UserProfile;
import io.github.alexandreclayton.tecdamandroid.R;
import io.github.alexandreclayton.tecdamandroid.Service.SpotifyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView txtName, txtQtdPlaylist, txtQtdFollowers, txtQtdFollowin;
    private CircleImageView profileImage;
    private SpotifyImpl spotify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        txtName = root.findViewById(R.id.txtName);
        txtQtdPlaylist = root.findViewById(R.id.txtQtdPlaylist);
        txtQtdFollowers = root.findViewById(R.id.txtQtdFollowers);
        txtQtdFollowin = root.findViewById(R.id.txtQtdFollowin);
        profileImage = root.findViewById(R.id.profile_image);

        if (!MainActivity.TOKEN.isEmpty()) {
            try {
                spotify = new SpotifyImpl(MainActivity.TOKEN);
                spotify.getSpotifyService().getProfile().enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, retrofit2.Response<UserProfile> response) {
                        if (response.isSuccessful()) {
                            UserProfile userProfile = response.body();
                            Picasso.with(getContext()).load(response.body().images.get(0).url).into(profileImage);
                            txtName.setText(userProfile.display_name);
                            txtQtdFollowers.setText(userProfile.followers.total.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        Log.e("QUERY", t.getMessage(), t);
                    }
                });
                spotify.getSpotifyService().getPlaylists().enqueue(new Callback<Playlist>() {
                    @Override
                    public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                        if (response.isSuccessful()) {
                            Playlist playlist = response.body();
                            txtQtdPlaylist.setText(playlist.total.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Playlist> call, Throwable t) {
                        Log.e("QUERY", t.getMessage(), t);
                    }
                });
                spotify.getSpotifyService().getFollowedArtists().enqueue(new Callback<FollowedArtists>() {
                    @Override
                    public void onResponse(Call<FollowedArtists> call, Response<FollowedArtists> response) {
                        if (response.isSuccessful()) {
                            FollowedArtists followedArtists = response.body();
                            txtQtdFollowin.setText(String.valueOf(followedArtists.artists.total));
                        }
                    }

                    @Override
                    public void onFailure(Call<FollowedArtists> call, Throwable t) {
                        Log.e("QUERY", t.getMessage(), t);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;
    }

}
