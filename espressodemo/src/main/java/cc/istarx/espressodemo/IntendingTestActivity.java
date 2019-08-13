package cn.istarx.espressodemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class IntendingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intending_test);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent();
        intent.putExtra("test", "Espresso Intending Test");
        setResult(RESULT_OK, intent);
        finish();
    }
}
