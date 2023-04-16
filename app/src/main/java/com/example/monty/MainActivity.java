package com.example.monty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {
                R.id.passive_btn,
                R.id.graphic_btn,
                R.id.text_btn,
                R.id.explain_btn,
                R.id.info_btn,
                R.id.tips_btn,
                R.id.guess_btn
        };

        Class[] activities = {
                PassiveActivity.class,
                GraphicActivity.class,
                TextActivity.class,
                ExplainActivity.class,
                InfoActivity.class,
                TipsActivity.class,
                GuessActivity.class
        };

        for (int i = 0; i < buttonIds.length; i++) {
            LinearLayout button = findViewById(buttonIds[i]);
            button.setOnClickListener(this);
            button.setTag(activities[i]);
        }
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
    }
}
