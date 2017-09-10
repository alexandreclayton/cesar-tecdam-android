package io.github.alexandreclayton.tecdamandroid.Service;

import io.github.alexandreclayton.tecdamandroid.Model.Pager;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistSimple;
import io.github.alexandreclayton.tecdamandroid.Model.UserPrivate;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by alexandresette on 09/09/17.
 */

public interface SpotifyService {


    @GET("/me")
    void getMe(Callback<UserPrivate> callback);

    @GET("/me")
    UserPrivate getMe();

    @GET("/me/playlists")
    void getMyPlaylists(Callback<Pager<PlaylistSimple>> callback);

    @GET("/me/playlists")
    Pager<PlaylistSimple> getMyPlaylists();
}
