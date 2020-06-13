package com.example.bottomtab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    AvatarAnimation avatarAnimation;
    ImageView avatar;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.avatar==null){
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            avatar = rootView.findViewById(R.id.avatar_image);
        }
        if(avatarAnimation==null){
            avatarAnimation = new AvatarAnimation(this, avatar);
        }
        return inflater.inflate(R.layout.fragment_home, container, false);
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
}
