package ytstudios.wall.bucket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by yugan on 11/23/2017.
 */

public class UtilityClass {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            Log.i("IS NETWORK", String.valueOf(activeNetworkInfo));
            Log.i("Connected", String.valueOf(activeNetworkInfo.isConnected()));
            return activeNetworkInfo.isConnected();
        } catch (Exception e) {
        }
        return false;
    }
}
