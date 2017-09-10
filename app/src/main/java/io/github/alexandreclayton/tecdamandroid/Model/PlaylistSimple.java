package io.github.alexandreclayton.tecdamandroid.Model;

import android.os.Parcel;

/**
 * Created by alexandresette on 10/09/17.
 */

public class PlaylistSimple extends PlaylistBase {
    public PlaylistTracksInformation tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.tracks, flags);
    }

    public PlaylistSimple() {
    }

    protected PlaylistSimple(Parcel in) {
        super(in);
        this.tracks = in.readParcelable(PlaylistTracksInformation.class.getClassLoader());
    }

    public static final Creator<PlaylistSimple> CREATOR = new Creator<PlaylistSimple>() {
        public PlaylistSimple createFromParcel(Parcel source) {
            return new PlaylistSimple(source);
        }

        public PlaylistSimple[] newArray(int size) {
            return new PlaylistSimple[size];
        }
    };
}
