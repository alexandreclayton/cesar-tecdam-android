package io.github.alexandreclayton.tecdamandroid.API;

import android.arch.lifecycle.MutableLiveData;

/**
 * Created by alexandresette on 05/10/17.
 */

public interface Spotify {
    public MutableLiveData getProfile();

    public MutableLiveData getPlaylists();
}
