package cc.istarx.robolectricdemo;


import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class RobolectricLooperDemo {

    public static void useHandle(){
        Handler handler = new Handler();
        Message message = new Message();
        message.what =1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something here;
                Log.d("zlz","lopper");
            }
        }, 100);
    }
}