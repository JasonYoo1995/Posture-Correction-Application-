package com.example.bottomtab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class DescriptionActivity extends Activity {
    ImageView currentArrow;
    int page = 1;
    Button prevButton;
    Button nextButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_description);
        prevButton = findViewById(R.id.prev);
        nextButton = findViewById(R.id.next);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = page-1;
                moveArrow(page);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = page+1;
                moveArrow(page);
            }
        });
        addBottomTabEvents();
    }

    private void addBottomTabEvents() {
        prevButton.setClickable(false);
        currentArrow = findViewById(R.id.arrow1);
        findViewById(R.id.description_tab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(1);
            }
        });
        findViewById(R.id.description_tab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(2);
            }
        });
        findViewById(R.id.description_tab3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(3);
            }
        });
        findViewById(R.id.description_tab4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(4);
            }
        });
        findViewById(R.id.description_tab5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(5);
            }
        });
        findViewById(R.id.description_tab6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(6);
            }
        });
        findViewById(R.id.description_tab7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(7);
            }
        });
        findViewById(R.id.description_tab8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(8);
            }
        });
    }

    private void moveArrow(int page){
        prevButton.setClickable(true);
        nextButton.setClickable(true);
        currentArrow.setVisibility(View.INVISIBLE);
        switch (page){
            case 1:
                prevButton.setClickable(false);
                this.page = 1;
                findViewById(R.id.arrow1).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow1);
                break;
            case 2:
                this.page = 2;
                findViewById(R.id.arrow2).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow2);
                break;
            case 3:
                this.page = 3;
                findViewById(R.id.arrow3).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow3);
                break;
            case 4:
                this.page = 4;
                findViewById(R.id.arrow4).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow4);
                break;
            case 5:
                this.page = 5;
                findViewById(R.id.arrow5).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow5);
                break;
            case 6:
                this.page = 6;
                findViewById(R.id.arrow6).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow6);
                break;
            case 7:
                this.page = 7;
                findViewById(R.id.arrow7).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow7);
                break;
            case 8:
                nextButton.setClickable(false);
                this.page = 8;
                findViewById(R.id.arrow8).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow8);
                break;
        }
    }

    private void makeToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
