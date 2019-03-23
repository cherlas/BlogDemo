package cn.istarx.robolectricdemo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class RobolectricDemoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robolectric_demo);

        findViewById(R.id.aty_jump).setOnClickListener(this);
        findViewById(R.id.show_dialog).setOnClickListener(this);
        findViewById(R.id.toast).setOnClickListener(this);
        findViewById(R.id.service).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.aty_jump:
                startActivity(new Intent(RobolectricDemoActivity.this, LoginActivity.class));
                break;
            case R.id.show_dialog:
                showDialog();
                break;
            case R.id.toast:
                Toast.makeText(this,"ToastTest",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(android.R.layout.select_dialog_item);
        dialog.setTitle("DialogTest");
        dialog.show();
    }
}
