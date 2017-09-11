package io.github.alexandreclayton.tecdamandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import io.github.alexandreclayton.tecdamandroid.API.SpotifyRetrofit;
import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.Model.UserPrivate;
import io.github.alexandreclayton.tecdamandroid.R;
import io.github.alexandreclayton.tecdamandroid.Service.SpotifyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        if (!MainActivity.TOKEN.isEmpty()) {
            try {
                SpotifyRetrofit retrofit = new SpotifyRetrofit(MainActivity.TOKEN);
                SpotifyService spotifyService = retrofit.getSpotifyService();

                spotifyService.getMe().enqueue(new Callback<UserPrivate>() {
                    @Override
                    public void onResponse(Call<UserPrivate> call, Response<UserPrivate> response) {
                        Log.i("Ret",response.body().display_name);
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
