package io.github.alexandreclayton.tecdamandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.github.alexandreclayton.tecdamandroid.fragments.PlayListFragment;
import io.github.alexandreclayton.tecdamandroid.fragments.PlayNowFragment;
import io.github.alexandreclayton.tecdamandroid.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {


    private PlayNowFragment playNowFragment;
    private PlayListFragment playListFragment;
    private ProfileFragment profileFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_playnow:
                    changeFragment(playNowFragment);
                    return true;
                case R.id.navigation_playlist:
                    changeFragment(playListFragment);
                    return true;
                case R.id.navigation_profile:
                    changeFragment(profileFragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.content) == null) {
            playNowFragment = new PlayNowFragment();
            playListFragment = new PlayListFragment();
            profileFragment = new ProfileFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.content, playNowFragment)
                    .add(R.id.content, playListFragment)
                    .add(R.id.content, profileFragment)
                    .hide(playListFragment)
                    .hide(profileFragment)
                    .commit();
        } else {
            buildFragments(fragmentManager);
        }



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void buildFragments(FragmentManager fragmentManager) {
        playNowFragment = (PlayNowFragment) fragmentManager.getFragments().get(0);
        playListFragment = (PlayListFragment) fragmentManager.getFragments().get(1);
        profileFragment = (ProfileFragment) fragmentManager.getFragments().get(2);
    }

    private void changeFragment(Fragment frg) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(playNowFragment)
                .hide(playListFragment)
                .hide(profileFragment)
                .show(frg).commit();
    }

}
