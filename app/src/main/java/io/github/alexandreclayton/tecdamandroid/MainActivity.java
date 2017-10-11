package io.github.alexandreclayton.tecdamandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.Locale;

import io.github.alexandreclayton.tecdamandroid.broadcast.ConnectionReceiver;
import io.github.alexandreclayton.tecdamandroid.fragments.PlayListFragment;
import io.github.alexandreclayton.tecdamandroid.fragments.PlayNowFragment;
import io.github.alexandreclayton.tecdamandroid.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {

    public static final String CLIENT_ID = "7e7bd3c704be43e192bdff63a4f9531f";
    public static String TOKEN = "";
    //public static final String CLIENT_SECRET = "7a587d8f0bb24dedae08caf1855a9495";
    //public static final String REDIRECT_URI = "tecdamandroid://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    public static final int AUTH_CODE_REQUEST_CODE = 0x11;

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
        if (ConnectionReceiver.isOnline(getApplicationContext())) {
            setContentView(R.layout.activity_main);
            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            if (ConnectionReceiver.isOnline(getApplicationContext())) {
                onRequestTokenClicked(this.getCurrentFocus());
            }
        } else {
            Intent intent = new Intent(this, NoConn.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            TOKEN = response.getAccessToken();
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            TOKEN = response.getAccessToken();
        }

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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onRequestTokenClicked(View view) {
        final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-private"
                        , "playlist-read-private"
                        , "user-follow-read"
                        , "streaming"
                        , "user-read-currently-playing"
                        , "user-read-playback-state"})
                .setCampaign("android-sdk")
                .build();
    }

    private Uri getRedirectUri() {
        return new Uri.Builder()
                .scheme("tecdamandroid")
                .authority("auth")
                .build();
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
