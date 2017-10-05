package io.github.alexandreclayton.tecdamandroid.Service;

import io.github.alexandreclayton.tecdamandroid.Model.Playlist;
import io.github.alexandreclayton.tecdamandroid.Model.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alexandresette on 09/09/17.
 */

public interface SpotifyService {


    @GET("me")
    Call<UserProfile> getProfile();

    @GET("me/playlists")
    Call<Playlist> getPlaylists();

}
