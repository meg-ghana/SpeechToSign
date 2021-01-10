package com.meghana.speechtosign;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Recording extends Fragment {
    public TextView texttv;
    public EditText editt;
    boolean isrecording = false;
    ImageView iv;
    TextView canc;
    Intent speechintent;
    TextView cont;
    SpeechRecognizer sr;
    public Recording() {
        // Required empty public constructor
    }
    public static Recording newInstance() {
        Recording fragment = new Recording();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root= inflater.inflate(R.layout.fragment_recording, container, false);
       texttv = root.findViewById(R.id.text);
       iv = root.findViewById(R.id.mic);
       editt = root.findViewById(R.id.edittext);
       cont = root.findViewById(R.id.cont);
       canc = root.findViewById(R.id.canc);
       return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},1);
            }
        }
        sr = SpeechRecognizer.createSpeechRecognizer(getActivity());
        speechintent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                //Toast.makeText(getActivity(), "error error eroor", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                String strtext = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                texttv.setText(strtext);
                iv.setColorFilter(Color.BLACK);
                ((RecordingActivity)getActivity()).message = strtext;
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                System.out.println("partial: "+partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen();
            }
        });
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelrec();
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrec();
            }
        });
    }

    public void listen(){
        System.out.println(isrecording);
        if(!isrecording){
            //start recording: change tv etc
            iv.setColorFilter(Color.RED);
            sr.startListening(speechintent);
            isrecording=true;
            texttv.setText("listening...");
        }
        else{
            isrecording=false;
            sr.stopListening();
            iv.setColorFilter(Color.BLACK);
        }
    }
    public void cancelrec(){
        isrecording=true;
        listen();

        //somehow cancel listener
    }
    public void contrec(){
        if(!editt.getText().toString().equals(""))
            ((RecordingActivity)getActivity()).message = editt.getText().toString();
        ((RecordingActivity)getActivity()).destroy_rec();
        //make main activity go to the other one
    }
}