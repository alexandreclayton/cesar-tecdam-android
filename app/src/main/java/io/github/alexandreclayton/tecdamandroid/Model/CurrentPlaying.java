package io.github.alexandreclayton.tecdamandroid.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandresette on 08/10/17.
 */

public class CurrentPlaying {

    @SerializedName("is_playing")
    public Boolean isPlaying;

    public CurrentPlayingItem item;
}
