package com.example.monty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ExplainActivity extends AppCompatActivity {

    private int loop = 0;
    private static final int[] DOOR_IMAGES = {
            R.drawable.door_closed,
            R.drawable.door_closed,
            R.drawable.door_closed,
            R.drawable.door_goat,
            R.drawable.door_car,
            R.drawable.door_goat
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        ImageView[] door = new ImageView[3];

        door[0] = findViewById(R.id.door_0);
        door[1] = findViewById(R.id.door_1);
        door[2] = findViewById(R.id.door_2);

        ImageView exit_btn = findViewById(R.id.exit_btn);

        exit_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
            finish();
        });

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable updateDoors = new Runnable() {
            @Override
            public void run() {
                door[loop % 3].setImageResource(DOOR_IMAGES[loop]);
                loop = (loop + 1) % DOOR_IMAGES.length;
                mainHandler.postDelayed(this, 1000);
            }
        };
        mainHandler.post(updateDoors);
    }
}
