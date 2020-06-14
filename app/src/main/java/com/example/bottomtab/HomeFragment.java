package com.example.bottomtab;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    AvatarAnimation avatarAnimation;
    ImageView avatar;
    TextView bubbleView;
    Button measureButton;
    View rootView;
    int state = 0; // 0: stopped  1: zero in  2: measuring

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        if(this.avatar==null){
            avatar = rootView.findViewById(R.id.avatar_image);
        }
        if(avatarAnimation==null){
            avatarAnimation = new AvatarAnimation(this, avatar);
        }
        bubbleView = rootView.findViewById(R.id.bubble_text);
        measureButton = rootView.findViewById(R.id.measuring_button);
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState();
                setBubbleText();
                setButtonText();
                setButtonColor();
            }
        });
        setBubbleText();
        setButtonText();
        setButtonColor();

        return rootView;
    }

    private void toggleState(){
        state+=1;
        state%=3;
    }

    private void setBubbleText(){
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

    private void setButtonText(){
        switch (state){
            case 0:
                measureButton.setText(R.string.button_text_state0);
                break;
            case 1:
                measureButton.setText(R.string.button_text_state1);
                break;
            case 2:
                measureButton.setText(R.string.button_text_state2);
                break;
        }
    }

    private void setButtonColor(){
        switch (state){
            case 0:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState0));
                break;
            case 1:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState1));
                break;
            case 2:
                ((GradientDrawable)measureButton.getBackground()).setColor(getResources().getColor(R.color.colorButtonState2));
                break;
        }
    }


    public void activate(){
        avatarAnimation.activate();
    }

    public void rotateAvatar(RotateAnimation rotateAnimation){
        avatar.startAnimation(rotateAnimation);
    }

    public void setAvatarTargetDegree(int targetDegree){
        avatarAnimation.setTarget(targetDegree);
    }

    private void makeToast(String string){
        Toast.makeText(this.getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
