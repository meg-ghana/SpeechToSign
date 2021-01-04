package com.meghana.speechtosign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void record(View v){
        Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
        startActivity(intent);
    }
    public void type(View v){

    }
}