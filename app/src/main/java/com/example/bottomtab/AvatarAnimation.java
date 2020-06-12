package com.example.bottomtab;

import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.LinkedList;
import java.util.Queue;

public class AvatarAnimation implements Runnable{
    RotateAnimation rotateAnimation;
    Thread lockThread;
    int duration;
    boolean lock, activated;
    int currentDegree;
    Queue<Integer> waitingQueue;
    MainActivity mainActivity;

    AvatarAnimation(MainActivity mainActivity){
        this.duration = 100;
        this.lock = false;
        this.activated = false;
        this.currentDegree = 0;
        this.waitingQueue = new LinkedList();
        this.mainActivity = mainActivity;
    }

    public void activate(){
        this.activated = true;
        this.lock = false;
        this.lockThread = new Thread(this);
        lockThread.start();
    }

    public void run(){
        while(activated){
            while(waitingQueue.isEmpty()){
                if(!activated) break;
            };
            if(!activated) break;

            setAnimationProperty();
            while(lock){} // waiting until the last animation ended
            rotate();

            lock = true;
            currentDegree = waitingQueue.peek();
            waitingQueue.remove();
        }
    }

    public void setTarget(int targetDegree){
        this.waitingQueue.add(targetDegree);
    }

    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            mainActivity.rotateAvatar((RotateAnimation)msg.obj);
        }
    };

    public void setAnimationProperty() {
        rotateAnimation = new RotateAnimation(currentDegree, waitingQueue.peek(),
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true); // maintain last degree

        rotateAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                lock = false;
            }
        });

    }

    public void rotate(){
        Message message = new Message();
        message.obj = rotateAnimation;
        handler.sendMessage(message);
    }

    public void deactivate(){
        activated = false;
    }
}
