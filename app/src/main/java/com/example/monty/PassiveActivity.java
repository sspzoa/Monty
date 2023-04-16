package com.example.monty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PassiveActivity extends AppCompatActivity {

    TextView maintain_cnt, change_cnt, maintain_result, change_result;
    ImageView[] door = new ImageView[3];
    Button maintain_btn, change_btn, reset_btn;

    Random random = new Random();
    int car = random.nextInt(3);
    int choice;
    int maintain_cnt_value = 0;
    int change_cnt_value = 0;
    int i = 0;
    int loop_reset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passive);
        initializeViews();
        setListeners();
    }

    private void initializeViews() {
        maintain_cnt = findViewById(R.id.maintain_cnt);
        change_cnt = findViewById(R.id.change_cnt);
        maintain_result = findViewById(R.id.maintain_result);
        change_result = findViewById(R.id.change_result);
        maintain_btn = findViewById(R.id.maintain_btn);
        change_btn = findViewById(R.id.change_btn);
        reset_btn = findViewById(R.id.reset_btn);
        door[0] = findViewById(R.id.door_0);
        door[1] = findViewById(R.id.door_1);
        door[2] = findViewById(R.id.door_2);

        maintain_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        change_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
    }

    private void setListeners() {
        findViewById(R.id.exit_btn).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            finish();
        });

        for (int i = 0; i < door.length; i++) {
            int finalI = i;
            door[i].setOnClickListener(view -> {
                select(door[finalI]);
                choice = finalI;
                return_door(0);
                btn_enable();
            });
        }

        maintain_btn.setOnClickListener(view -> updateGame(1));
        change_btn.setOnClickListener(view -> updateGame(2));

        reset_btn.setOnClickListener(view -> {
            maintain_cnt_value = 0;
            maintain_result_cnt = 0;
            change_cnt_value = 0;
            change_result_cnt = 0;

            maintain_cnt.setText("성공: □/□");
            maintain_result.setText("결과: □□.□%");
            change_cnt.setText("성공: □/□");
            change_result.setText("결과: □□.□%");

            reset_door();
            btn_disable();
        });
    }

    private void updateGame(int btn_value) {
        i = 0;
        loop_reset = 0;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (++i < 3) {
                    loop_reset++;

                    switch (loop_reset) {
                        case 1:
                            return_door(btn_value);
                            btn_disable();
                            print_result(btn_value);
                            new Handler(Looper.getMainLooper()).postDelayed(this, 1000);
                            break;
                        case 2:
                            reset_door();
                            break;
                    }
                }
            }
        });
    }

    private void select(ImageView selected_door) {
        for (ImageView door : door) {
            door.setImageResource(R.drawable.door_closed);
            door.setClickable(false);
        }
        selected_door.setImageResource(R.drawable.door_closed_originalpick);
    }

    private void reset_door() {
        for (ImageView door : door) {
            door.setImageResource(R.drawable.door_closed);
            door.setClickable(true);
        }
        car = random.nextInt(3);
    }

    private void btn_enable() {
        maintain_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
        change_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));

        maintain_btn.setEnabled(true);
        change_btn.setEnabled(true);
    }

    private void btn_disable() {
        maintain_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        change_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));

        maintain_btn.setEnabled(false);
        change_btn.setEnabled(false);
    }

    private void return_door(int btn_value) {
        open_public();
        if (btn_value > 0) {
            if (btn_value == 1) {
                open_maintain_result();
            } else {
                open_change_result();
            }
        }
    }

    ImageView change_door;

    private void open_public() {
        int[] other_doors = {0, 1, 2};
        other_doors[choice] = -1;
        int door1 = other_doors[0] == -1 ? other_doors[1] : other_doors[0];
        int door2 = other_doors[2] == -1 ? other_doors[1] : other_doors[2];

        if (choice == car) {
            int temp = random.nextInt(2);
            if (temp == 0) {
                door[door1].setImageResource(R.drawable.door_goat_public);
                change_door = door[door2];
            } else {
                door[door2].setImageResource(R.drawable.door_goat_public);
                change_door = door[door1];
            }
        } else {
            door[door2].setImageResource(R.drawable.door_goat_public);
            change_door = door[door1];
        }
    }

    int maintain_result_cnt = 0;
    int change_result_cnt = 0;

    private void open_maintain_result() {
        if (choice == car) {
            door[choice].setImageResource(R.drawable.door_car_originalpick);
            maintain_result_cnt++;
        } else {
            door[choice].setImageResource(R.drawable.door_goat_originalpick);
        }
    }

    private void open_change_result() {
        if (choice == car) {
            change_door.setImageResource(R.drawable.door_goat_changedpick);
        } else {
            change_door.setImageResource(R.drawable.door_car_changedpick);
            change_result_cnt++;
        }
    }

    private void print_result(int btn_value) {
        switch (btn_value) {
            case 1:
                maintain_cnt_value++;
                maintain_cnt.setText("성공: " + maintain_result_cnt + "/" + maintain_cnt_value);
                maintain_result.setText("결과: " + String.format("%.1f", ((double) maintain_result_cnt / maintain_cnt_value) * 100) + "%");
                break;
            case 2:
                change_cnt_value++;
                change_cnt.setText("성공: " + change_result_cnt + "/" + change_cnt_value);
                change_result.setText("결과: " + String.format("%.1f", ((double) change_result_cnt / change_cnt_value) * 100) + "%");
        }
    }
}
