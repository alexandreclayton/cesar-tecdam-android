package io.github.alexandreclayton.tecdamandroid.Service;

import io.github.alexandreclayton.tecdamandroid.Model.PlaylistSimple;
import io.github.alexandreclayton.tecdamandroid.Model.UserPrivate;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alexandresette on 09/09/17.
 */

public interface SpotifyService {


    @GET("/me")
    Call<UserPrivate> getMe();

    @GET("/me/playlists")
    Call<PlaylistSimple> getMyPlaylists();
}
