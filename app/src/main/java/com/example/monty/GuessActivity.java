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

    private Random random = new Random();
    private ImageView[] omr = new ImageView[5];
    private TextView commandView, percentageView, answerView;
    private Button calculateBtn;
    private int[] omrValue = {0, 0, 0, 0, 0};
    private int pick, loop = 0, blue = 0, white = 0, choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        initViews();
        setOnClickListeners();
    }

    private void initViews() {
        commandView = findViewById(R.id.command_view);
        percentageView = findViewById(R.id.percentage_view);
        answerView = findViewById(R.id.answer_view);
        calculateBtn = findViewById(R.id.calculate_btn);

        for (int i = 0; i < 5; i++) {
            omr[i] = findViewById(getResources().getIdentifier("omr_" + (i + 1), "id", getPackageName()));
        }

        calculateBtn.setBackgroundColor(Color.parseColor("#B2B2B2"));
    }

    private void setOnClickListeners() {
        findViewById(R.id.exit_btn).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            finish();
        });

        View.OnClickListener omrClickListener = view -> {
            pick = view.getId() - R.id.omr_1;
            buttonMethod((ImageView) view);
        };

        for (ImageView imageView : omr) {
            imageView.setOnClickListener(omrClickListener);
        }

        calculateBtn.setOnClickListener(view -> calculate());
    }

    private void buttonMethod(ImageView omr) {
        select(omr);

        if (blue != 0) {
            calculateBtn.setEnabled(true);
            calculateBtn.setBackgroundColor(Color.parseColor("#5D6DBE"));
        }
    }

    private void select(ImageView selectedOmr) {
        if (loop == 0) {
            selectedOmr.setImageResource(R.drawable.omr_red);
            commandView.setText("제외할 정답을 선택해주세요.");
            omrValue[pick] = 1;
        } else {
            blue++;

            if (blue < 4) {
                selectedOmr.setImageResource(R.drawable.omr_blue);
                omrValue[pick] = 2;
            } else {
                Toast.makeText(getApplicationContext(), "적어도 하나의 선택지는 남아야 합니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        loop++;
        selectedOmr.setClickable(false);
    }

    private void calculate() {
        calculateBtn.setEnabled(false);
        calculateBtn.setBackgroundColor(Color.parseColor("#B2B2B2"));
        commandView.setText("가장 확률이 높은 번호입니다.");

        for (ImageView imageView : omr) {
            imageView.setClickable(false);
        }

        count();

        int temp;
        do {
            temp = random.nextInt(5);
        } while (omrValue[temp] == 1 || omrValue[temp] == 2);

        omr[temp].setImageResource(R.drawable.omr_filled);

        percentageView.setText(String.format("%.0f", ((double) 4 / 5 / white) * 100) + "% 확률로");
        answerView.setText(temp + 1 + "번");
    }

    private void count() {
        white = 0;

        for (int i = 0; i < omrValue.length; i++) {
            if (omrValue[i] == 0) {
                white++;
            } else if (omrValue[i] == 1) {
                choice = i + 1;
            }
        }
    }
}
