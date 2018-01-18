package com.sheygam.masa_2017_g2_18_01_18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar myProgress;
    private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.start_btn);
        myProgress = findViewById(R.id.my_progress);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
            new MyThread(new MyThread.Callback() {
                @Override
                public void onStartWorker() {
                    runOnUiThread(() -> {
                        myProgress.setVisibility(View.VISIBLE);
                        startBtn.setEnabled(false);
                    });
                }

                @Override
                public void onStopWorker() {
                    runOnUiThread(() -> {
                        myProgress.setVisibility(View.INVISIBLE);
                        startBtn.setEnabled(true);
                    });
                }
            }).start();
        }
    }
}
