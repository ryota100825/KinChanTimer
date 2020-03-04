package com.example.kinchantimer;

import java.util.TimerTask;

public class FiveMinutesCountDown extends CountDown {
    private int count = 300;

    public void excute() {
        timer.schedule(new TimerTask() {
            public void run() {
                if (count > 0 && isRunning) {
                    count--;
                } else if (count <= 0) {
                    timer.cancel();
                    notifyObservers();
                    changeIsFinished();
                }
            }
        }, 0, 1000l);
    }

    public int getMinutes() {
        return countToMinutes(count);
    }

    public int getSeconds() {
        return countToSeconds(count);
    }
}
