package com.example.bottomtab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;

public class InitialActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_initial);
    }
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
