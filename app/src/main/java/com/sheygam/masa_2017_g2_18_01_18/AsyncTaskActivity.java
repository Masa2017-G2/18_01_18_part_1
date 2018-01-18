package com.sheygam.masa_2017_g2_18_01_18;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn, stopBtn;
    private MyTask myTask;
    private ProgressBar myProgress, downloadProgress;
    private TextView resultTxt,countTxt,downloadTxt,downloadProgressTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        myProgress = findViewById(R.id.my_progress);
        resultTxt = findViewById(R.id.result_txt);
        downloadProgress = findViewById(R.id.download_progress);
        countTxt = findViewById(R.id.count_txt);
        downloadTxt = findViewById(R.id.download);
        downloadProgressTxt = findViewById(R.id.download_prog_txt);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
           myTask = new MyTask();
           myTask.execute();
        }else if(v.getId() == R.id.stop_btn){
            myTask.cancel(true);
        }
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{
        private int totalCount;
        private static final int COUNT_STATUS = 0x01;
        private static final int CURRENT_COUNT = 0x02;
        private static final int DOWNLOAD_PROGRESS = 0x03;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startBtn.setEnabled(false);
            myProgress.setVisibility(View.VISIBLE);
            downloadProgress.setVisibility(View.VISIBLE);
            downloadTxt.setVisibility(View.VISIBLE);
            resultTxt.setVisibility(View.INVISIBLE);
            stopBtn.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]){
                case COUNT_STATUS:
                    totalCount = values[1];
                    countTxt.setVisibility(View.VISIBLE);
                    countTxt.setText(String.valueOf(totalCount));
                    break;
                case CURRENT_COUNT:
                    downloadProgressTxt.setVisibility(View.VISIBLE);
                    String str = values[1] + "/" + totalCount;
                    downloadProgressTxt.setText(str);
                    break;
                case DOWNLOAD_PROGRESS:
                    downloadProgress.setProgress(values[1]);
                    break;
            }
        }

        @Override
        protected String doInBackground(Integer... ints) {
            Random rnd = new Random();
            int count = rnd.nextInt(15);
            publishProgress(COUNT_STATUS,count+1);
            for (int i = 0; i <= count; i++) {
                publishProgress(CURRENT_COUNT,i+1);
                for (int j = 0; j < 100; j++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(DOWNLOAD_PROGRESS,j+1);
                    Log.d("MY_TAG", "doInBackground: j = " + j);
                    if (isCancelled()){
                        return "Was canceled";
                    }
                }
            }

            return "All done!";
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            startBtn.setEnabled(true);
            myProgress.setVisibility(View.INVISIBLE);
            resultTxt.setText(str);
            resultTxt.setVisibility(View.VISIBLE);
            downloadTxt.setVisibility(View.INVISIBLE);
            downloadProgress.setVisibility(View.INVISIBLE);
            downloadProgressTxt.setVisibility(View.INVISIBLE);
            stopBtn.setEnabled(false);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            onPostExecute(s);
        }
    }
}
