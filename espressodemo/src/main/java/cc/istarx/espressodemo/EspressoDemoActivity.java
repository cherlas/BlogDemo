package cn.istarx.espressodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EspressoDemoActivity extends AppCompatActivity {

    private Button loginButton;
    private Button intendingTestButton;
    private EditText emailText;
    private EditText passwordText;
    TextView result_text;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso_demo);

        loginButton = findViewById(R.id.login);
        intendingTestButton = findViewById(R.id.intending_button);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        result_text = findViewById(R.id.result_text);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(EspressoDemoActivity.this,ShowResultActivity.class);
            intent.putExtra("email", emailText.getText().toString());
            intent.putExtra("password",passwordText.getText().toString());
            startActivity(intent);
        });

        intendingTestButton.setOnClickListener((v) -> {
            Intent intent  = new Intent("cn.istarx.espressodemo.customaction");
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            String testStr = data.getStringExtra("test");
            result_text.setText(testStr);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
