package io.github.alexandreclayton.tecdamandroid.Model;

import java.util.List;

/**
 * Created by alexandresette on 05/10/17.
 */

public class FollowedBase<T> {
    public String href;
    public List<T> items;
    public int total;
}
