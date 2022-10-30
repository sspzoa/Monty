package com.example.monty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout passive_btn, graphic_btn, text_btn, explain_btn, info_btn, tips_btn, guess_btn;

        passive_btn = (LinearLayout) findViewById(R.id.passive_btn);
        graphic_btn = (LinearLayout) findViewById(R.id.graphic_btn);
        text_btn = (LinearLayout) findViewById(R.id.text_btn);
        explain_btn = (LinearLayout) findViewById(R.id.explain_btn);
        info_btn = (LinearLayout) findViewById(R.id.info_btn);
        tips_btn = (LinearLayout) findViewById(R.id.tips_btn);
        guess_btn = (LinearLayout) findViewById(R.id.guess_btn);

        passive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PassiveActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        graphic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphicActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        explain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExplainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        tips_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TipsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });

        guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            }
        });
    }
}