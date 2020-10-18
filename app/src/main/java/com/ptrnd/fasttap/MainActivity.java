package com.ptrnd.fasttap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlertDialog instructDialog;
    Button startBtn, mainBtn;
    TextView tv_info;
    long startTime, endTime, currentTime, bestTime = 10000;

    @Override
    protected void onStart() {
        super.onStart();
        instructDialog = new AlertDialog.Builder(MainActivity.this).create();
        instructDialog.setTitle("Welcome.");
        instructDialog.setMessage("Just start the game, then tap the button fast, then you will know how many millisecond your respond is.");
        instructDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        instructDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.startBtn);
        mainBtn = (Button) findViewById(R.id.mainBtn);
        tv_info = (TextView) findViewById(R.id.tv_info);

        startBtn.setEnabled(true);
        mainBtn.setEnabled(false);
        tv_info.setText("Best Time: " + bestTime + " ms");

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBtn.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startBtn.setEnabled(false);
                        mainBtn.setEnabled(true);
                        startTime = System.currentTimeMillis();
                        mainBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                        mainBtn.setText("NOW!");
                    }
                }, 2000);
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = System.currentTimeMillis();
                currentTime = endTime-startTime;
                mainBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                mainBtn.setText(currentTime + " MS");
                startBtn.setEnabled(true);
                mainBtn.setEnabled(false);

                if (currentTime < bestTime){
                    Toast.makeText(getApplicationContext(), "You had a best time, congrats!",
                            Toast.LENGTH_LONG).show();
                    bestTime = currentTime;
                    tv_info.setText("Best Time: " + bestTime + " ms");
                }
            }
        });
    }
}