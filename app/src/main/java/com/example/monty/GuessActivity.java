package com.example.monty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GuessActivity extends AppCompatActivity {

    Random random = new Random();
    ImageView[] omr = new ImageView[5];
    TextView command_view, percentage_view, answer_view;
    Button calculate_btn;
    int[] omr_value = {0, 0, 0, 0, 0};
    int pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        command_view = (TextView) findViewById(R.id.command_view);
        percentage_view = (TextView) findViewById(R.id.percentage_view);
        answer_view = (TextView) findViewById(R.id.answer_view);
        calculate_btn = (Button) findViewById(R.id.calculate_btn);

        omr[0] = (ImageView) findViewById(R.id.omr_1);
        omr[1] = (ImageView) findViewById(R.id.omr_2);
        omr[2] = (ImageView) findViewById(R.id.omr_3);
        omr[3] = (ImageView) findViewById(R.id.omr_4);
        omr[4] = (ImageView) findViewById(R.id.omr_5);

        calculate_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));

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

        omr[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick = 0;

                button_method(omr[0]);

            }

        });

        omr[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick = 1;

                button_method(omr[1]);
            }
        });

        omr[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick = 2;

                button_method(omr[2]);

            }
        });

        omr[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick = 3;

                button_method(omr[3]);

            }
        });

        omr[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick = 4;

                button_method(omr[4]);

            }
        });

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calculate();

            }
        });

    }

    public void button_method(ImageView omr) {

        select(omr);

        if (blue != 0) {

            calculate_btn.setEnabled(true);
            calculate_btn.setBackgroundColor(Color.parseColor("#5D6DBE"));
        }

    }

    int loop = 0;
    int blue = 0;

    public void select(ImageView selected_omr) {

        if (loop == 0) {
            selected_omr.setImageResource(R.drawable.omr_red);

            command_view.setText("제외할 정답을 선택해주세요.");

            omr_value[pick] = 1;

        } else {

            blue++;

            if (blue < 4) {

                selected_omr.setImageResource(R.drawable.omr_blue);

                omr_value[pick] = 2;

            } else {
                Toast.makeText(getApplicationContext(), "적어도 하나의 선택지는 남아야 합니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        loop++;

        selected_omr.setClickable(false);
    }

    int white = 0;
    int choice;

    public void calculate() {

        calculate_btn.setEnabled(false);
        calculate_btn.setBackgroundColor(Color.parseColor("#B2B2B2"));

        command_view.setText("가장 확률이 높은 번호입니다.");

        omr[0].setClickable(false);
        omr[1].setClickable(false);
        omr[2].setClickable(false);
        omr[3].setClickable(false);
        omr[4].setClickable(false);

        count();

        int temp = 0;

        for (int i = 0; i < 1; i++) {
            temp = random.nextInt(5);

            if (omr_value[temp] == 1 || omr_value[temp] == 2) {
                i--;
            }
        }

        omr[temp].setImageResource(R.drawable.omr_filled);

        percentage_view.setText(String.format("%.0f", ((double) 4 / 5 / white) * 100 ) + "% 확률로");
        answer_view.setText(temp + 1 + "번");

    }

    public void count() {

        // 하양은 0, 빨강은 1, 파랑은 2

        switch (omr_value[0]) {
            case 0:
                white++;
                break;
            case 1:
                choice = 1;
                break;
        }

        switch (omr_value[1]) {
            case 0:
                white++;
                break;
            case 1:
                choice = 2;
                break;
        }

        switch (omr_value[2]) {
            case 0:
                white++;
                break;
            case 1:
                choice = 3;
                break;
        }

        switch (omr_value[3]) {
            case 0:
                white++;
                break;
            case 1:
                choice = 4;
                break;
        }

        switch (omr_value[4]) {
            case 0:
                white++;
                break;
            case 1:
                choice = 5;
                break;
            case 2:
        }
    }
}
