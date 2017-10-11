package io.github.alexandreclayton.tecdamandroid.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import io.github.alexandreclayton.tecdamandroid.MainActivity;
import io.github.alexandreclayton.tecdamandroid.NoConn;
import io.github.alexandreclayton.tecdamandroid.R;

public class ConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                Toast.makeText(context, R.string.msgConnectionRestored, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            } else {
                Toast.makeText(context, R.string.msgNoConection, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(context, NoConn.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
