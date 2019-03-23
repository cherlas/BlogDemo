package cn.istarx.robolectricdemo;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyService extends IntentService {
    public MyService() {
        this("test");
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // do something here
    }
}
