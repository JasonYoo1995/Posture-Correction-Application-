package com.example.bottomtab;

import android.bluetooth.BluetoothAdapter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    MainActivity mainActivity; // MainActivity에 있는 블루투스 연결을 제어하기 위한 용도
    View rootView; // HomeFragment 자기 자신에 대한 객체 (Activity와 달리 Fragment는 this로 해결되지 않는 부분이 있음)
    TextView postureData; // 실시간 기울기 정보(x축, y축)를 아바타 오른쪽에 텍스트로 띄움
    TextView bubbleView; // 아바타 상단의 말풍선
    ImageView avatarFront; // 정면 아바타
    ImageView avatarSide; // 측면 아바타
    ImageView helpButton; // 도움말 버튼
    ImageView helpBox; // 도움말 상자
    Button measureButton; // 측정 버튼
    int state = 0; // HomeFragment의 3가지 상태 : 0 = 대기 상태, 1 = 영점 조절 중, 2 = 측정 중

    HomeFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        avatarFront = rootView.findViewById(R.id.avatar_front);
        avatarSide = rootView.findViewById(R.id.avatar_side);
        bubbleView = rootView.findViewById(R.id.bubble_text);
        helpBox = rootView.findViewById(R.id.help_zero);
        helpBox.setVisibility(View.INVISIBLE);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    helpBox.setVisibility(View.INVISIBLE); // 아무데나 클릭 시, 도움말 상자 사라짐
                }
                return true;
            }
        });
        helpButton = rootView.findViewById(R.id.help_button_in_home);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBox.setVisibility(View.VISIBLE); // 도움말 버튼 클릭 시, 도움말 상자 나타냄
            }
        });
        measureButton = rootView.findViewById(R.id.measuring_button);
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBox.setVisibility(View.INVISIBLE); // 측정 버튼 클릭 시, 도움말 상자 사라짐
                toggleState(); // 상태 전환
            }
        });
        setBubbleText(); // 현재 state에 맞는 말풍선 설정
        setButtonProperty(); // 현재 state에 맞는 버튼 설정
//        postureData = rootView.findViewById(R.id.received_posture_data);

        return rootView;
    }

    public void setState0(){ // 상태 0에 맞는 화면 설정
        state = 0;
        setBubbleText();
        setButtonProperty();
        setAvatarAngle(90,90);
    }

    // 아바타 이미지의 각도 설정 : (정면 아바타 각도, 측면 아바타 각도)
    // (input -> output) = (180도 -> -90도), (90도 -> 0도), (0도 -> 90도)
    public void setAvatarAngle(int front, int side){
        int frontAngle = 90-front;
        int sideAngle = 90-side;
        if(frontAngle<-90 || frontAngle>90 || frontAngle<-90 || sideAngle>90) return; // 회전 각도 범위는 180도로 제한
        avatarFront.setRotation(frontAngle); // 각도 적용
        avatarSide.setRotation(sideAngle); // 각도 적용
    }

//    public void setPostureData(String string){
//        postureData.setText(string);
//    }

    private void toggleState(){ // 상태 전환
        if(BluetoothAdapter.getDefaultAdapter().isEnabled()){ // 블루투스가 활성화 되어있는 경우
            if(state==0){
                mainActivity.listPairedDevices(); // 페어링 가능한 장치들 중 우리 기기를 찾아서 연결
            }
            if(state==1){
                makeToast("영점 조절을 했습니다.");
            }
            if(state==2){
                makeToast("기기와 연결을 해제했습니다.");
                mainActivity.mThreadConnectedBluetooth.cancel(); // 기기와 연결 끊기
                setState0();
                return;
            }
            // state 순환 토글
            state+=1;
            state%=3;
            setBubbleText();
            setButtonProperty();
        }
        else { // 블루투스가 비활성화 되어있는 경우
            makeToast("블루투스를 켜고 다시 시도하세요.");
            setState0();
        }
    }

    public void setBubbleText(){ // 말풍선 내용 설정
        switch (state){
            case 0:
                bubbleView.setText(R.string.bubble_text_state0);
                break;
            case 1:
                bubbleView.setText(R.string.bubble_text_state1);
                break;
            case 2:
                bubbleView.setText(R.string.bubble_text_state2);
                break;
        }
    }

    public void setButtonProperty(){ // 측정 버튼 색깔 / 측정 버튼 텍스트 / 도움말 버튼 설정,
        switch (state){
            case 0:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState0));
                measureButton.setText(R.string.button_text_state0);
                helpButton.setVisibility(View.INVISIBLE);
                break;
            case 1:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState1));
                measureButton.setText(R.string.button_text_state1);
                helpButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState2));
                measureButton.setText(R.string.button_text_state2);
                helpButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void makeToast(String string){ // 토스트
        Toast.makeText(this.getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
