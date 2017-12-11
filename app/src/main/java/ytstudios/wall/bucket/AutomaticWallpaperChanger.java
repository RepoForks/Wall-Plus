package ytstudios.wall.bucket;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yugansh on 12/1/2017.
 */

public class AutomaticWallpaperChanger extends Service {
    Timer timer = new Timer();
    ArrayList<String> paths = new ArrayList<>();

    public void onCreate() {

        super.onCreate();
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        paths = intent.getStringArrayListExtra("paths");
        TimerTask updateProfile = new AutomaticTimerTask(AutomaticWallpaperChanger.this, paths);
        timer.scheduleAtFixedRate(updateProfile, 0, 7000);
        Log.d("PATHS", paths.toString());
        Log.d("PATHS SIZE", String.valueOf(paths.size()));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        timer.cancel();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}

class AutomaticTimerTask extends TimerTask {

    private Context context;
    private ArrayList<String> wallpapers;
    private Handler mHandler = new Handler();
    int i = 1;
    private WallpaperManager automaticWallpaperManager;
    DisplayMetrics dm;
    int height,width;

    public AutomaticTimerTask(Context context, ArrayList<String> paths) {
        this.context = context;
        this.wallpapers = paths;
        automaticWallpaperManager = WallpaperManager.getInstance(this.context);
        dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels;
        width = dm.widthPixels;
    }

    @Override
    public void run() {
        new Thread(new Runnable() {

            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        //ToasterManager.setBitmap(result);
                        automaticWallpaperManager.suggestDesiredDimensions(width, height);
                        try {
                            automaticWallpaperManager.setBitmap(BitmapFactory.decodeFile(wallpapers.get(i)));
                        }catch (IOException e){

                        }
                        i++;
                        Log.d("Wallpapers Size ", String.valueOf(wallpapers.size()));
                        Log.d("I", String.valueOf(i));

                    }
                });
            }
        }).start();
    }
}
