package cn.istarx.robolectricdemo;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

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
