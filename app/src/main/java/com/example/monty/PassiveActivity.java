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
    int car;
    int choice;
    int maintain_cnt_value = 0;
    int change_cnt_value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passive);

        initializeViews();

        reset_btn.setOnClickListener(view -> {
            maintain_cnt_value = 0;
            change_cnt_value = 0;
            maintain_result_cnt = 0;
            change_result_cnt = 0;

            updateResults();

            resetDoors();
            disableButtons();
        });

        car = random.nextInt(3);

        for (int i = 0; i < door.length; i++) {
            int index = i;
            door[i].setOnClickListener(view -> {
                selectDoor(index);
                enableButtons();
            });
        }

        maintain_btn.setOnClickListener(view -> {
            loopReset(1);
            updateResults();
            disableButtons();
        });

        change_btn.setOnClickListener(view -> {
            loopReset(2);
            updateResults();
            disableButtons();
        });
    }

    private void initializeViews() {
        maintain_cnt = findViewById(R.id.maintain_cnt);
        change_cnt = findViewById(R.id.change_cnt);
        maintain_result = findViewById(R.id.maintain_result);
        change_result = findViewById(R.id.change_result);
        maintain_btn = findViewById(R.id.maintain_btn);
        change_btn = findViewById(R.id.change_btn);
        reset_btn = findViewById(R.id.reset_btn);

        ImageView exit_btn = findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            finish();
        });

        door[0] = findViewById(R.id.door_0);
        door[1] = findViewById(R.id.door_1);
        door[2] = findViewById(R.id.door_2);
    }

    private void selectDoor(int index) {
        choice = index;
        for (int i = 0; i < door.length; i++) {
            door[i].setClickable(false);
            if (i == index) {
                door[i].setImageResource(R.drawable.door_closed_originalpick);
            }
        }
    }

    private void resetDoors() {
        for (int i = 0; i < door.length; i++) {
            door[i].setImageResource(R.drawable.door_closed);
            door[i].setClickable(true);
        }
        car = random.nextInt(3);
    }

    private void enableButtons() {
        maintain_btn.setEnabled(true);
        change_btn.setEnabled(true);
        maintain_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
        change_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
    }

    private void disableButtons() {
        maintain_btn.setEnabled(false);
        change_btn.setEnabled(false);
        maintain_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        change_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
    }

    int maintain_result_cnt = 0;
    int change_result_cnt = 0;

    private void loopReset(int btn_value) {
        ImageView[] otherDoors = getOtherDoors();
        int goatDoorIndex = getGoatDoorIndex(otherDoors);
        otherDoors[goatDoorIndex].setImageResource(R.drawable.door_goat_public);

        if (btn_value == 1) {
            if (choice == car) {
                door[choice].setImageResource(R.drawable.door_car_originalpick);
                maintain_result_cnt++;
            } else {
                door[choice].setImageResource(R.drawable.door_goat_originalpick);
            }
        } else {
            ImageView change_door = otherDoors[1 - goatDoorIndex];
            if (choice != car) {
                change_door.setImageResource(R.drawable.door_car_changedpick);
                change_result_cnt++;
            } else {
                change_door.setImageResource(R.drawable.door_goat_changedpick);
            }
        }
        resetDoors();
    }

    private ImageView[] getOtherDoors() {
        ImageView[] otherDoors = new ImageView[2];
        int index = 0;
        for (int i = 0; i < door.length; i++) {
            if (i != choice) {
                otherDoors[index++] = door[i];
            }
        }
        return otherDoors;
    }

    private int getGoatDoorIndex(ImageView[] otherDoors) {
        int goatDoorIndex;
        if (choice == car) {
            goatDoorIndex = random.nextInt(2);
        } else {
            goatDoorIndex = otherDoors[0] == door[car] ? 1 : 0;
        }
        return goatDoorIndex;
    }

    private void updateResults() {
        maintain_cnt.setText("성공: " + maintain_result_cnt + "/" + maintain_cnt_value);
        maintain_result.setText("결과: " + String.format("%.1f", ((double) maintain_result_cnt / maintain_cnt_value) * 100) + "%");
        change_cnt.setText("성공: " + change_result_cnt + "/" + change_cnt_value);
        change_result.setText("결과: " + String.format("%.1f", ((double) change_result_cnt / change_cnt_value) * 100) + "%");
    }
}
