package com.example.monty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView cnt, maintain_result, change_result, maintain_cnt, change_cnt;
        Button start_btn;
        EditText testno_etext;

        cnt = (TextView) findViewById(R.id.cnt);
        maintain_result = (TextView) findViewById(R.id.maintain_result);
        change_result = (TextView) findViewById(R.id.change_result);
        start_btn = (Button) findViewById(R.id.start_btn);
        testno_etext = (EditText) findViewById(R.id.testno_etext);
        maintain_cnt = (TextView) findViewById(R.id.maintain_cnt);
        change_cnt = (TextView) findViewById(R.id.change_cnt);

        Random random = new Random();

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

        start_btn.setOnClickListener(new View.OnClickListener() {

            int i = 1, maintain_result_cnt = 0, change_result_cnt = 0, choice = 0, speed = 0;

            @Override
            public void onClick(View view) {

                if (testno_etext.length() == 0) {
                    Toast.makeText(getApplicationContext(), "횟수를 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                } else {

                    testno_etext.setFocusable(false);
                    testno_etext.setFocusableInTouchMode(false);

                    int testno = Integer.parseInt(testno_etext.getText().toString());

                    i = 0;
                    maintain_result_cnt = 0;
                    change_result_cnt = 0;
                    speed++;

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (++i < testno + 1) {

                                maintain();

                                change();

                                printResult();

                                start_btn_function(testno);

                                new Handler(Looper.getMainLooper()).postDelayed(this, 100);
                            }
                        }
                    });

                }
            }

            int car;

            public void maintain() {

                car = random.nextInt(3);

                if (choice == car) {
                    maintain_result_cnt++;
                }
            }

            public void change() {

                car = random.nextInt(3);

                switch (car) {
                    case 0:
                        choice = random.nextInt(2) + 1;
                        break;
                    case 1:
                        choice = 1;
                        break;
                    case 2:
                        choice = 2;
                        break;
                }

                if (choice == car) {
                    change_result_cnt++;
                }
            }

            public void printResult() {

                cnt.setText("횟수: " + i);
                maintain_result.setText("결과: " + String.format("%.3f", ((double) maintain_result_cnt / i) * 100) + "%");
                change_result.setText("결과: " + String.format("%.3f", ((double) change_result_cnt / i) * 100) + "%");
                maintain_cnt.setText("성공: " + maintain_result_cnt);
                change_cnt.setText("성공: " + change_result_cnt);
                start_btn.setText("X" + speed);
            }

            public void start_btn_function(int testno) {
                if (i == testno) {
                    speed = 0;
                    start_btn.setText("시작");

                    testno_etext.setFocusable(true);
                    testno_etext.setFocusableInTouchMode(true);
                }
            }
        });
    }
}