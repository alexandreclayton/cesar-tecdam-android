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
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1";

    private SpotifyService spotifyService;

    private String TOKEN = "";

    public SpotifyRetrofit(SpotifyService spotifyService) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json;versions=1");
                        ongoing.addHeader("Authorization", "Bearer " + TOKEN);
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_WEB_API_ENDPOINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spotifyService = retrofit.create(SpotifyService.class);

    }
}
