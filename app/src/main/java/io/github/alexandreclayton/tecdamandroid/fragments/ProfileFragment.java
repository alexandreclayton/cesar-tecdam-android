package io.github.alexandreclayton.tecdamandroid.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.alexandreclayton.tecdamandroid.API.SpotifyRetrofit;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistBase;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistSimple;
import io.github.alexandreclayton.tecdamandroid.Model.UserPrivate;
import io.github.alexandreclayton.tecdamandroid.R;
import io.github.alexandreclayton.tecdamandroid.Service.SpotifyService;
import io.github.alexandreclayton.tecdamandroid.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextView txtName, txtQtdPlaylist, txtQtdFollowers, txtQtdFollowin;
    CircleImageView profileImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final UserPrivate[] userPrivate = {new UserPrivate()};
        txtName = root.findViewById(R.id.txtName);
        txtQtdPlaylist = root.findViewById(R.id.txtQtdPlaylist);
        txtQtdFollowers = root.findViewById(R.id.txtQtdFollowers);
        txtQtdFollowin = root.findViewById(R.id.txtQtdFollowin);
        profileImage = root.findViewById(R.id.profile_image);

        if (!MainActivity.TOKEN.isEmpty()) {
            try {
                SpotifyRetrofit retrofit = new SpotifyRetrofit(MainActivity.TOKEN);
                SpotifyService spotifyService = retrofit.getSpotifyService();


                spotifyService.getMe().enqueue(new Callback<UserPrivate>() {
                    @Override
                    public void onResponse(Call<UserPrivate> call, Response<UserPrivate> response) {

                        Picasso.with(getContext()).load(response.body().images.get(0).url).into(profileImage);
                        txtName.setText(response.body().display_name);
                        txtQtdFollowers.setText(response.body().followers.total.toString());
                    }

                    @Override
                    public void onFailure(Call<UserPrivate> call, Throwable t) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;
    }

}
