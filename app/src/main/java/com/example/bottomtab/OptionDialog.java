package com.example.bottomtab;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class OptionDialog extends Dialog {
    ImageView helpButton, helpBoxAlert, exitButton;

    public OptionDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.layout_option);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawableResource(android.R.color.background_light);

        helpBoxAlert = findViewById(R.id.help_alert);
        helpBoxAlert.setVisibility(View.INVISIBLE);
        helpButton = findViewById(R.id.help_button_in_option);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBoxAlert.setVisibility(View.VISIBLE);
            }
        });

        exitButton = findViewById(R.id.button_option_exit);
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                helpBoxAlert.setVisibility(View.INVISIBLE);
                dismiss();
            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        helpBoxAlert.setVisibility(View.INVISIBLE);
        return super.onTouchEvent(event);
    }
}
