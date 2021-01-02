package com.example.bottomtab.etc;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.bottomtab.R;

public class InstructionActivity extends Activity {
    ViewFlipper viewFlipper; // 화면 전환 매니저
    ImageView currentArrow; // 화살표 이미지 : 현재 몇 번째 탭인지 가리킴
    ImageView exitButton; // 닫기 버튼
    TextView title; // 제목
    Button prevButton; // 이전 버튼
    Button nextButton; // 다음 버튼
    int page = 1; // 페이지 번호 (1~8)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_instruction);
        prevButton = findViewById(R.id.prev);
        nextButton = findViewById(R.id.next);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 이전 버튼
                page = page-1;
                moveArrow(page);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 다음 버튼
                page = page+1;
                moveArrow(page);
            }
        });
        addBottomTabEvents(); // 탭 버튼에 이벤트 추가
        title = findViewById(R.id.title_instruction);
        exitButton = findViewById(R.id.button_instruction_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }); // 닫기
        viewFlipper = findViewById(R.id.page_area);
    }

    private void addBottomTabEvents() {
        prevButton.setClickable(false); // 1번 화면에서는 이전 버튼 클릭 불가
        currentArrow = findViewById(R.id.arrow1); // 현재는 1번 탭을 가리킴
        findViewById(R.id.instruction_tab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveArrow(1); // 1페이지로 화살표 이동
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
                prevButton.setClickable(false); // 1번 화면에서는 이전 버튼 클릭 불가
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
                nextButton.setClickable(false); // 8번 화면에서는 다음 버튼 클릭 불가
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
