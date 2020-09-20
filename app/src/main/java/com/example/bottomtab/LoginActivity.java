package com.example.bottomtab;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        findViewById(R.id.layout_sign_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 계정 생성 버튼 누르면 다음 화면
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout_sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 계정 생성 버튼 누르면 다음 화면
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
