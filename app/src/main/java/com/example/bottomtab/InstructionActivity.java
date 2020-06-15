package com.example.bottomtab;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class InstructionActivity extends Activity {
    ImageView currentArrow;
    int page = 1;
    Button prevButton;
    Button nextButton;
    TextView title;
    ImageView exitButton;
    ViewFlipper viewFlipper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_instruction);
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
        title = findViewById(R.id.title_instruction);
        exitButton = findViewById(R.id.button_instruction_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewFlipper = findViewById(R.id.page_area);
    }

    private void addBottomTabEvents() {
        prevButton.setClickable(false);
        currentArrow = findViewById(R.id.arrow1);
        findViewById(R.id.instruction_tab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(1);
            }
        });
        findViewById(R.id.instruction_tab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(2);
            }
        });
        findViewById(R.id.instruction_tab3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(3);
            }
        });
        findViewById(R.id.instruction_tab4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(4);
            }
        });
        findViewById(R.id.instruction_tab5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(5);
            }
        });
        findViewById(R.id.instruction_tab6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(6);
            }
        });
        findViewById(R.id.instruction_tab7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(7);
            }
        });
        findViewById(R.id.instruction_tab8).setOnClickListener(new View.OnClickListener() {
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
        viewFlipper.setDisplayedChild(page-1);
        switch (page){
            case 1:
                prevButton.setClickable(false);
                this.page = 1;
                findViewById(R.id.arrow1).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow1);
                title.setText(R.string.instruction_title_1);
                break;
            case 2:
                this.page = 2;
                findViewById(R.id.arrow2).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow2);
                title.setText(R.string.instruction_title_2);
                break;
            case 3:
                this.page = 3;
                findViewById(R.id.arrow3).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow3);
                title.setText(R.string.instruction_title_3);
                break;
            case 4:
                this.page = 4;
                findViewById(R.id.arrow4).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow4);
                title.setText(R.string.instruction_title_4);
                break;
            case 5:
                this.page = 5;
                findViewById(R.id.arrow5).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow5);
                title.setText(R.string.instruction_title_5);
                break;
            case 6:
                this.page = 6;
                findViewById(R.id.arrow6).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow6);
                title.setText(R.string.instruction_title_6);
                break;
            case 7:
                this.page = 7;
                findViewById(R.id.arrow7).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow7);
                title.setText(R.string.instruction_title_7);
                break;
            case 8:
                nextButton.setClickable(false);
                this.page = 8;
                findViewById(R.id.arrow8).setVisibility(View.VISIBLE);
                currentArrow = findViewById(R.id.arrow8);
                title.setText(R.string.instruction_title_8);
                break;
        }
    }

    private void makeToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
