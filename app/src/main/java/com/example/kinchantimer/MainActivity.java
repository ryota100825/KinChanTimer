package com.example.kinchantimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private Timer timer;
    private Handler timerHandler = new Handler();

    private CountDown countDown = new ThreeMinutesCountDown();
    private boolean isFirstProcess = true;

    // 各種ボタンのインスタンス
    private TextView countDownText;
    private Button buttonStartPause;
    private Button buttonReset;
    private Button buttonThreeMinutes;
    private Button buttonFiveMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownText = findViewById(R.id.countDownText);
        buttonStartPause = findViewById(R.id.button_start_pause);
        buttonReset = findViewById(R.id.button_reset);
        buttonThreeMinutes = findViewById(R.id.button_three_minutes);
        buttonFiveMinutes = findViewById(R.id.button_five_minutes);

        buttonThreeMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDown = new ThreeMinutesCountDown();
            }
        });

        buttonFiveMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDown = new FiveMinutesCountDown();
            }
        });

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDown.getisRunning() == false && isFirstProcess) { // タイマー初動時
                    startTimer(countDown);
                } else if (countDown.getisRunning() == false) {            // タイマーが動いていないとき
                    continueTimer(countDown);
                } else {
                    pauseTimer(countDown);
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        timerExucute();
    }

    private void startTimer(CountDown countDown) {
        countDown.changeIsRunnning();
        countDown.excute();
        isFirstProcess = !isFirstProcess;
        buttonThreeMinutes.setVisibility(View.INVISIBLE);
        buttonFiveMinutes.setVisibility(View.INVISIBLE);
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setText("Stop");
    }

    private void continueTimer(CountDown countDown) {
        countDown.changeIsRunnning();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setText("Stop");
    }

    private void pauseTimer(CountDown countDown) {
        countDown.changeIsRunnning();
        buttonReset.setVisibility(View.VISIBLE);
        buttonStartPause.setText("Start");
    }

    private void resetTimer() {
        isFirstProcess = !isFirstProcess;
        buttonThreeMinutes.setVisibility(View.VISIBLE);
        buttonFiveMinutes.setVisibility(View.VISIBLE);
        countDown = new ThreeMinutesCountDown();
        timerExucute();
    }

    private void timerExucute() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (countDown.getIsFinished() == false) {
                            updateCountDownText(countDown);
                        } else {
                            timer.cancel();
                            playVibrator();
                        }
                    }
                });
            }
        }, 0, 200l);
    }

    private void playVibrator() {
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }

    private void updateCountDownText(CountDown countDown) {
        String timerLeft = String.format(Locale.getDefault(), "%02d:%02d", countDown.getMinutes(), countDown.getSeconds());
        countDownText.setText(timerLeft);
    }

}
