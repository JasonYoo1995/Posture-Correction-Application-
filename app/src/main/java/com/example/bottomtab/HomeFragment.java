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
    ImageView avatarFront;
    ImageView avatarSide;
    TextView bubbleView;
    Button measureButton;
    View rootView;
    ImageView helpButton;
    ImageView helpBoxZero;
    MainActivity mainActivity;
    TextView postureData;

    int state = 0; // 0: stopped  1: zero in  2: measuring

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
        helpBoxZero = rootView.findViewById(R.id.help_zero);
        helpBoxZero.setVisibility(View.INVISIBLE);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    helpBoxZero.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
        helpButton = rootView.findViewById(R.id.help_button_in_home);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBoxZero.setVisibility(View.VISIBLE);
            }
        });
        measureButton = rootView.findViewById(R.id.measuring_button);
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpBoxZero.setVisibility(View.INVISIBLE);
                toggleState();
            }
        });
        setBubbleText();
        setButtonProperty();

        postureData = rootView.findViewById(R.id.received_posture_data);

        return rootView;
    }

    public void initializeHome(){
        state = 0;
        setBubbleText();
        setButtonProperty();
        setImageDegree(90,90);
    }

    public void setImageDegree(int degreeFront, int degreeSide){
        int front = 180-degreeFront-90;
        int side = 180-degreeSide-90;
        if(front<-90 || front>90 || front<-90 || side>90) return;
        avatarFront.setRotation(180-degreeFront-90);
        avatarSide.setRotation(180-degreeSide-90);
    }

    public void setPostureData(String string){
        postureData.setText(string);
    }

    private void toggleState(){
        if(BluetoothAdapter.getDefaultAdapter().isEnabled()){
            if(state==0){
                mainActivity.listPairedDevices();
            }
            if(state==1){
                makeToast("영점 조절을 했습니다.");
            }
            if(state==2){
                makeToast("기기와 연결을 해제했습니다.");
                mainActivity.mThreadConnectedBluetooth.cancel();
                initializeHome();
                return;
            }
            state+=1;
            state%=3;
        }
        else{
            makeToast("블루투스를 켜고 다시 시도하세요.");
            if(state==0) return;
            else state = 0;
        }
        setBubbleText();
        setButtonProperty();
    }

    public void setBubbleText(){
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

    public void setButtonProperty(){
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

    private void makeToast(String string){
        Toast.makeText(this.getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
