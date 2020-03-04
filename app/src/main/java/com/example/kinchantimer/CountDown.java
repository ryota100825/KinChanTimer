package com.example.kinchantimer;

import java.util.Timer;

public abstract class CountDown {
    protected Timer timer = new Timer();

    protected int minutes;
    protected int seconds;
    protected boolean isRunning = false;
    protected boolean isFinished = false;

    public void notifyObservers() {
        Observer observer = new PlaySoundObserver();
        observer.finish();
    }

    public abstract void excute();

    public abstract int getMinutes();

    public abstract int getSeconds();

    public void changeIsRunnning() {
        isRunning = !isRunning;
    }

    public boolean getisRunning() {
        return isRunning;
    }

    public void changeIsFinished() {
        isFinished = !isFinished;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    protected int countToMinutes(int count) {
        minutes = count / 60;
        return minutes;
    }

    protected int countToSeconds(int count) {
        seconds = count % 60;
        return seconds;
    }
}
