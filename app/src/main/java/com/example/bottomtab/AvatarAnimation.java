package com.example.bottomtab;

import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class AvatarAnimation implements Runnable{
    RotateAnimation rotateAnimation;
    Thread lockThread;
    int duration;
    boolean activated;
    int currentDegree;
    ArrayList<Integer> waitingQueue;
    HomeFragment homeFragment;
    ImageView avatar;
    private ReentrantLock mutex = new ReentrantLock();

    AvatarAnimation(HomeFragment homeFragment, ImageView imageView){
        this.duration = 100;
        this.activated = false;
        this.currentDegree = 0;
        this.waitingQueue = new ArrayList<Integer>();
        this.homeFragment = homeFragment;
        this.avatar = imageView;
    }

    public void activate(){
        this.activated = true;
        this.lockThread = new Thread(this);
        lockThread.start();
    }

    public void run(){
        while(activated){
            setAnimationProperty();
            rotate();
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTarget(int targetDegree){
        mutex.lock();
        this.waitingQueue.add(targetDegree);
        mutex.unlock();
        System.out.println("add " + waitingQueue);
    }

    final Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            homeFragment.rotateAvatar((RotateAnimation)msg.obj);
        }
    };

    public void setAnimationProperty() {
        while(waitingQueue.isEmpty()) {}
        int targetDegree = waitingQueue.get(0);
        System.out.println("current " + currentDegree + "  target " + targetDegree);
        rotateAnimation = new RotateAnimation(currentDegree, targetDegree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true); // maintain last degree

        rotateAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                System.out.println("animation started");
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                System.out.println("animation finished");
            }
        });

    }

    public void rotate(){
        Message message = new Message();
        message.obj = rotateAnimation;
        handler.sendMessage(message);

        if(waitingQueue.isEmpty()) return;
        mutex.lock();
        currentDegree = waitingQueue.get(0);
        waitingQueue.remove(0);
        mutex.unlock();
        System.out.println("remove " + waitingQueue);
    }

    public void deactivate(){
        activated = false;
    }
}
