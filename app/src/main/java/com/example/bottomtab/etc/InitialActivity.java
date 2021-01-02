package com.example.bottomtab.etc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;

import com.example.bottomtab.R;

// 초기 화면
public class InitialActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_initial);
    }
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) { // 아무데나 클릭하면 다음 화면
            case (MotionEvent.ACTION_DOWN) :
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
