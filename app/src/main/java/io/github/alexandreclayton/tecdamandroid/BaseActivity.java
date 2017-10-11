package io.github.alexandreclayton.tecdamandroid;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by alexandresette on 11/10/17.
 */

public class BaseActivity extends AppCompatActivity {
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    @Override
    protected void onResume() {
        super.onResume();
        activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPaused();
    }
}
