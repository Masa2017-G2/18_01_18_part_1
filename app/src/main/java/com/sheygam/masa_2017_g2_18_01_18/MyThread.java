package com.sheygam.masa_2017_g2_18_01_18;

/**
 * Created by gregorysheygam on 18/01/2018.
 */

public class MyThread extends Thread {
    private Callback callback;

    public MyThread(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onStartWorker();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        callback.onStopWorker();
    }

    public interface Callback{
        void onStartWorker();
        void onStopWorker();
    }
}
