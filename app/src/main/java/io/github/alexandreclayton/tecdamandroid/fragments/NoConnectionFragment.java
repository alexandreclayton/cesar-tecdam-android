package io.github.alexandreclayton.tecdamandroid.fragments;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoConnectionFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }

}
