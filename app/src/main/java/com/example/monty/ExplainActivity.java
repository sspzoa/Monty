package com.example.monty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExplainActivity extends AppCompatActivity {

    int loop = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        ImageView[] door = new ImageView[3];

        door[0] = (ImageView) findViewById(R.id.door_0);
        door[1] = (ImageView) findViewById(R.id.door_1);
        door[2] = (ImageView) findViewById(R.id.door_2);

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

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                switch (loop) {
                    case 0:
                        door[0].setImageResource(R.drawable.door_closed);
                        break;
                    case 1:
                        door[1].setImageResource(R.drawable.door_closed);
                        break;
                    case 2:
                        door[2].setImageResource(R.drawable.door_closed);
                        break;
                    case 3:
                        door[0].setImageResource(R.drawable.door_goat);
                        break;
                    case 4:
                        door[1].setImageResource(R.drawable.door_car);
                        break;
                    case 5:
                        door[2].setImageResource(R.drawable.door_goat);
                        loop = -1;
                        break;
                }

                loop++;

                new Handler(Looper.getMainLooper()).postDelayed(this, 1000);
            }
        });

    }
}