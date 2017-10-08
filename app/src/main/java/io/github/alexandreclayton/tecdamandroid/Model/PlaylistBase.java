package io.github.alexandreclayton.tecdamandroid.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by alexandresette on 10/09/17.
 */

public class PlaylistBase {

    public Boolean collaborative;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public List<Image> images;
    public String name;
    public PlaylistOwner owner;
    @SerializedName("public")
    public Boolean is_public;
    public Tracks tracks;
    public String snapshot_id;
    public String type;
    public String uri;
    public Integer total;

}
