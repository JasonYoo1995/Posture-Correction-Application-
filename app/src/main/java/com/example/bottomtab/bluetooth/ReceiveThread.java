package com.example.bottomtab.bluetooth;


import com.example.bottomtab.etc.MainActivity;

public class ReceiveThread implements Runnable{
    private boolean stop;
    private Thread thread;
    MainActivity mainActivity;

    public ReceiveThread(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        stop = true;
    }

    public void run() {
        long start = System.currentTimeMillis();
        while(!stop){
            long current = System.currentTimeMillis();
            if(current-start>200){
                start+=200;
                mainActivity.getPostureData();
            }
        }
        stop = true;
    }

    public void startReceive(){
        this.thread = new Thread(this);
        thread.start();
        stop = false;
    }

    public void stopReceive() {
        this.stop = true;
    }
}
