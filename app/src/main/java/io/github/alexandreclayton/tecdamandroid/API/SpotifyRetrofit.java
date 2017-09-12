package io.github.alexandreclayton.tecdamandroid.API;

import java.io.IOException;

import io.github.alexandreclayton.tecdamandroid.Service.SpotifyService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandresette on 10/09/17.
 */

public class SpotifyRetrofit {
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/";

    private SpotifyService spotifyService;

    private String Token = "";

    public SpotifyRetrofit(String token) {
        setToken(token);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Authorization", "Bearer " + getToken());
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_WEB_API_ENDPOINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        setSpotifyService(retrofit.create(SpotifyService.class));

    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public SpotifyService getSpotifyService() {
        return spotifyService;
    }

    public void setSpotifyService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
}
