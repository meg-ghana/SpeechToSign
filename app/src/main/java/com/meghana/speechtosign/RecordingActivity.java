package com.meghana.speechtosign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class RecordingActivity extends AppCompatActivity {
    public Recording rec;
    public RecordVideos rv;
    String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        rec = new Recording();
        FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
        transact.replace(R.id.fragment,rec);
        transact.addToBackStack(null);
        transact.commit();
    }
    public void destroy_rec(){
        rv = new RecordVideos();
        FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
        transact.replace(R.id.fragment,rv);
        transact.addToBackStack(null);
        transact.commit();
    }
}