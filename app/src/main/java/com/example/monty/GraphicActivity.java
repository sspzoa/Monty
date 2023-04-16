package com.example.monty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GraphicActivity extends AppCompatActivity {

    private TextView maintain_cnt, change_cnt, maintain_result, change_result;
    private ImageView[] maintain_door = new ImageView[3];
    private ImageView[] change_door = new ImageView[3];
    private Button start_btn;
    private ImageView exit_btn;
    private Random random = new Random();

    private int i, maintain_resultcnt, change_resultcnt, choice, car, loop_maintain, loop_change, loop_printresult, ii;
    private final int testno = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        initializeViews();
        setExitButtonClickListener();
        setStartButtonClickListener();
    }

    private void initializeViews() {
        maintain_cnt = findViewById(R.id.maintain_cnt);
        change_cnt = findViewById(R.id.change_cnt);
        maintain_result = findViewById(R.id.maintain_result);
        change_result = findViewById(R.id.change_result);

        for (int i = 0; i < 3; i++) {
            maintain_door[i] = findViewById(getResources().getIdentifier("maintain_door_" + i, "id", getPackageName()));
            change_door[i] = findViewById(getResources().getIdentifier("change_door_" + i, "id", getPackageName()));
        }

        start_btn = findViewById(R.id.start_btn);
        exit_btn = findViewById(R.id.exit_btn);
    }

    private void setExitButtonClickListener() {
        exit_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            finish();
        });
    }

    private void setStartButtonClickListener() {
        start_btn.setOnClickListener(view -> {
            i = 0;
            maintain_resultcnt = 0;
            change_resultcnt = 0;
            start_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
            start_btn.setText("실행중");
            start_btn.setEnabled(false);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (++i < testno + 1) {
                        maintain();
                        change();
                        printResult();
                        new Handler(Looper.getMainLooper()).postDelayed(this, 2100);
                    }
                }
            });
        });
    }

    private void maintain() {
        int temp = random.nextInt(2) + 1;
        car = random.nextInt(3);
        ii = 0;
        loop_maintain = 0;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (++ii < 5) {
                    loop_maintain++;
                    switch (loop_maintain) {
                        case 1:
                            maintain_door[1].setImageResource(R.drawable.door_closed_originalpick);
                            break;
                        case 2:
                            maintain_door[temp == 1 ? 0 : 2].setImageResource(R.drawable.door_goat_public);
                            break;
                        case 3:
                            if (car == 0) {
                                maintain_resultcnt++;
                                maintain_door[1].setImageResource(R.drawable.door_car_originalpick);
                            } else {
                                maintain_door[1].setImageResource(R.drawable.door_goat_originalpick);
                            }
                            break;
                        case 4:
                            start_btn_function();
                            close_all();
                            break;
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(this, 500);
                }
            }
        });
    }

    private void change() {
        int temp = random.nextInt(2) + 1;
        car = random.nextInt(3);
        loop_change = 0;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (ii < 5) {
                    loop_change++;
                    switch (loop_change) {
                        case 1:
                            change_door[1].setImageResource(R.drawable.door_closed_originalpick);
                            break;
                        case 2:
                            change_door[car != 2 ? 2 : 0].setImageResource(R.drawable.door_goat_public);
                            break;
                        case 3:
                            switch (car) {
                                case 0:
                                    change_door[temp == 1 ? 2 : 0].setImageResource(R.drawable.door_goat_changedpick);
                                    break;
                                case 1:
                                    choice = 1;
                                    change_door[0].setImageResource(R.drawable.door_car_changedpick);
                                    break;
                                case 2:
                                    choice = 2;
                                    change_door[2].setImageResource(R.drawable.door_car_changedpick);
                                    break;
                            }
                            if (choice == car) {
                                change_resultcnt++;
                            }
                            break;
                        case 4:
                            break;
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(this, 500);
                }
            }
        });
    }

    private void printResult() {
        loop_printresult = 0;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (ii < 5) {
                    loop_printresult++;

                    if (loop_maintain == 3) {
                        maintain_cnt.setText("성공: " + maintain_resultcnt + "/" + i);
                        change_cnt.setText("성공: " + change_resultcnt + "/" + i);
                        maintain_result.setText("결과: " + String.format("%.1f", ((double) maintain_resultcnt / i) * 100) + "%");
                        change_result.setText("결과: " + String.format("%.1f", ((double) change_resultcnt / i) * 100) + "%");
                    }
                    new Handler(Looper.getMainLooper()).postDelayed(this, 500);
                }
            }
        });
    }

    private void start_btn_function() {
        if (i == testno) {
            start_btn.setText("시작");
            start_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
            start_btn.setEnabled(true);
        }
    }

    private void close_all() {
        for (ImageView door : maintain_door) {
            door.setImageResource(R.drawable.door_closed);
        }
        for (ImageView door : change_door) {
            door.setImageResource(R.drawable.door_closed);
        }
    }
}