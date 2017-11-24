package com.jrbaselibrary.scheduled;

/**
 * Created by zhush on 2017/11/14.
 * Email 405086805@qq.com
 */

public abstract class Task {

    public int interval;
    public boolean run;
    public int count;
    public int max;

    public void start() {
        run = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (run) {
                    count = count + 1;
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Task.this.run();

                    if (count == max) {
                        run = false;
                    }
                }


            }
        }).start();
    }


    public void cancel() {
        run = false;
    }


    public abstract void run();

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
