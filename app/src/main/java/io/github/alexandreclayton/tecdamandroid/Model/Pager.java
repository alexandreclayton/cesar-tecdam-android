package io.github.alexandreclayton.tecdamandroid.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandresette on 10/09/17.
 */

public class Pager<T> implements Parcelable {
    public String href;
    public ArrayList items;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeList(items);
        dest.writeInt(limit);
        dest.writeString(next);
        dest.writeInt(offset);
        dest.writeString(previous);
        dest.writeInt(total);
    }

    public Pager() {
    }

    protected Pager(Parcel in) {
        this.href = in.readString();
        this.items = in.readArrayList(ArrayList.class.getClassLoader());
        this.limit = in.readInt();
        this.next = in.readString();
        this.offset = in.readInt();
        this.previous = in.readString();
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<Pager> CREATOR = new Parcelable.Creator<Pager>() {
        public Pager createFromParcel(Parcel source) {
            return new Pager(source);
        }

        public Pager[] newArray(int size) {
            return new Pager[size];
        }
    };
}
