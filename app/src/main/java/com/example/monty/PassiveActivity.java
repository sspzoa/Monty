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

        maintain_cnt = (TextView) findViewById(R.id.maintain_cnt);
        change_cnt = (TextView) findViewById(R.id.change_cnt);
        maintain_result = (TextView) findViewById(R.id.maintain_result);
        change_result = (TextView) findViewById(R.id.change_result);
        maintain_btn = (Button) findViewById(R.id.maintain_btn);
        change_btn = (Button) findViewById(R.id.change_btn);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        door[0] = (ImageView) findViewById(R.id.door_0);
        door[1] = (ImageView) findViewById(R.id.door_1);
        door[2] = (ImageView) findViewById(R.id.door_2);

        maintain_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        change_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));

        ImageView exit_btn;

        exit_btn = (ImageView) findViewById(R.id.exit_btn);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
                finish();
            }
        });

        door[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select(door[0], door[1], door[2]);
                choice = 0;
                loop = 0;

                return_door(0);
                btn_enable();

            }
        });

        door[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select(door[1], door[0], door[2]);
                choice = 1;
                loop = 0;

                return_door(0);
                btn_enable();

            }
        });

        door[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select(door[2], door[0], door[1]);
                choice = 2;
                loop = 0;

                return_door(0);
                btn_enable();

            }
        });

        maintain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 0;
                loop_reset = 0;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (++i < 3) {
                            loop_reset++;

                            switch (loop_reset) {
                                case 1:
                                    return_door(1);
                                    btn_disable();
                                    print_result(1);
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
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 0;
                loop_reset = 0;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (++i < 3) {
                            loop_reset++;

                            switch (loop_reset) {
                                case 1:
                                    return_door(2);
                                    btn_disable();
                                    print_result(2);
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
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

    }

    public void select(ImageView selected_door, ImageView door1, ImageView door2) {
        selected_door.setImageResource(R.drawable.door_closed_originalpick);

        selected_door.setClickable(false);
        door1.setClickable(false);
        door2.setClickable(false);

    }

    public void reset_door() {

        door[0].setImageResource(R.drawable.door_closed);
        door[1].setImageResource(R.drawable.door_closed);
        door[2].setImageResource(R.drawable.door_closed);

        door[0].setClickable(true);
        door[1].setClickable(true);
        door[2].setClickable(true);

        car = random.nextInt(3);
    }

    public void btn_enable() {
        maintain_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
        change_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));

        maintain_btn.setEnabled(true);
        change_btn.setEnabled(true);
    }

    public void btn_disable() {
        maintain_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        change_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));

        maintain_btn.setEnabled(false);
        change_btn.setEnabled(false);
    }

    int loop = 0;

    public void return_door(int btn_value) {

        loop++;

        switch (loop) {
            case 1:
                switch (choice) {
                    case 0:
                        open_public(door[1], door[2]);
                        break;
                    case 1:
                        open_public(door[0], door[2]);
                        break;
                    case 2:
                        open_public(door[0], door[1]);
                        break;
                }
                break;
            case 2:
                switch (btn_value) {
                    case 1:
                        open_maintain_result();
                        break;
                    case 2:
                        open_change_result();
                        break;
                }
                break;
        }
    }

    ImageView change_door;

    public void open_public(ImageView door1, ImageView door2) {

        if (choice == car) {
            int temp = random.nextInt(2);

            switch (temp) {
                case 0:
                    door1.setImageResource(R.drawable.door_goat_public);
                    change_door = door2;
                    break;
                case 1:
                    door2.setImageResource(R.drawable.door_goat_public);
                    change_door = door1;
                    break;
            }
        } else {
            switch (choice) {
                case 0:
                    switch (car) {
                        case 1:
                            door2.setImageResource(R.drawable.door_goat_public);
                            change_door = door1;
                            break;
                        case 2:
                            door1.setImageResource(R.drawable.door_goat_public);
                            change_door = door2;
                            break;
                    }
                    break;
                case 1:
                    switch (car) {
                        case 0:
                            door2.setImageResource(R.drawable.door_goat_public);
                            change_door = door1;
                            break;
                        case 2:
                            door1.setImageResource(R.drawable.door_goat_public);
                            change_door = door2;
                            break;
                    }
                    break;
                case 2:
                    switch (car) {
                        case 0:
                            door2.setImageResource(R.drawable.door_goat_public);
                            change_door = door1;
                            break;
                        case 1:
                            door1.setImageResource(R.drawable.door_goat_public);
                            change_door = door2;
                            break;
                    }
                    break;
            }
        }
    }

    int maintain_result_cnt = 0;
    int change_result_cnt = 0;

    public void open_maintain_result() {
        if (choice == car) {
            door[choice].setImageResource(R.drawable.door_car_originalpick);
            maintain_result_cnt++;
        } else {
            door[choice].setImageResource(R.drawable.door_goat_originalpick);
        }
    }

    public void open_change_result() {
        if (choice == car) {
            change_door.setImageResource(R.drawable.door_goat_changedpcik);
        } else {
            change_door.setImageResource(R.drawable.door_car_changedpick);
            change_result_cnt++;
        }
    }

    public void print_result(int btn_value) {
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