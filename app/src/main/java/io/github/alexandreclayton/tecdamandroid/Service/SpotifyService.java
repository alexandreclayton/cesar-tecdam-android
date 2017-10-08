package io.github.alexandreclayton.tecdamandroid.Service;

import io.github.alexandreclayton.tecdamandroid.Model.FollowedArtists;
import io.github.alexandreclayton.tecdamandroid.Model.Pager;
import io.github.alexandreclayton.tecdamandroid.Model.Playlist;
import io.github.alexandreclayton.tecdamandroid.Model.PlaylistTrack;
import io.github.alexandreclayton.tecdamandroid.Model.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by alexandresette on 09/09/17.
 */

public interface SpotifyService {


    @GET("me")
    Call<UserProfile> getProfile();

    @GET("users/{user_id}")
    Call<UserProfile> getProfile(@Path("user_id") String id);

    @GET("me/playlists")
    Call<Playlist> getPlaylists();

    @GET("me/following?type=artist")
    Call<FollowedArtists> getFollowedArtists();

    @GET("users/{user_id}/playlists/{playlist_id}/tracks")
    Call<Pager<PlaylistTrack>> getPlaylistTraks(@Path("user_id") String user_id, @Path("playlist_id") String playlist_id);

}
