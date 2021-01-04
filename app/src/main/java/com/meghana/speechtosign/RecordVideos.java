package com.meghana.speechtosign;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class RecordVideos extends Fragment {
    String umessage="";
//    TextView tv;
//    TextView name;
    LinearLayout linlay;
    ScrollView scroll;
    ArrayList<String> al;
    public RecordVideos() {
        // Required empty public constructor
    }

    public static RecordVideos newInstance() {
        RecordVideos fragment = new RecordVideos();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_record_videos, container, false);
        umessage = ((RecordingActivity)getActivity()).message;

        umessage = umessage.trim().replace("'", "");
        String[] arr = umessage.split(" ");
        String new_ms="";
        al = new ArrayList<String>();
        if(umessage.contains(" ")){
            String s = "and the is am are or to ";
            for(int i = 0;i<arr.length;i++){
                if( !arr[i].equals("")) {
                    if (arr[i].equals("dont")){
                        al.add("dont-"+arr[i+1]);
                        i+=1;
                    }
                    else if(arr[i].equalsIgnoreCase("I")){
                        al.add("me");
                    }
                    else if(s.contains(arr[i])){
                        Toast.makeText(getActivity(), "doesnt work: "+arr[i], Toast.LENGTH_LONG).show();
                    }
                    else{
                        al.add(arr[i]);
                    }
                }
            }
            umessage=al.get(0);
        }
        else{
            al.add(umessage);
        }
        for(String s: al){
            System.out.println("heyyeyey"+s);
        }

        System.out.println(umessage);
        scroll = v.findViewById(R.id.sv);
        linlay = v.findViewById(R.id.linlay);
       // tv = v.findViewById(R.id.textv);
       // name = v.findViewById(R.id.names);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i<al.size();i++) {
            ScrollView.LayoutParams lay2 = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linlay.setLayoutParams(lay2);

            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lay.setMargins(300,40,300,0);

            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(30f);
            tv.setText(al.get(i).replace("-"," "));
            if(al.get(i).equals("me")){
                tv.setText("me/I");
            }
            tv.setGravity(Gravity.CENTER_HORIZONTAL);

            CardView cv = new CardView(getActivity());
            cv.setRadius(10);
            cv.setLayoutParams(lay);
            cv.addView(tv);
            linlay.addView(cv);

            VideoView vidv = new VideoView(getActivity());
            CardView cv2 = new CardView(getActivity());
            cv2.setRadius(10);
            cv2.setLayoutParams(lay);
            cv2.addView(vidv);
            linlay.addView(cv2);
            if(al.get(i).equals("cat")){
                System.out.println("hello");
            }
            MediaController mc = new MediaController(getActivity());
            mc.setAnchorView(vidv);
            try {
              //  name.setText(umessage);
                Uri video = Uri.parse("https://media.signbsl.com/videos/asl/startasl/mp4/" + al.get(i) + ".mp4");
                // Toast.makeText(getActivity(), "work"+al.get(i), Toast.LENGTH_LONG).show();
                vidv.setMediaController(mc);
                vidv.setVideoURI(video);
                vidv.start();
                vidv.pause();

            } catch (Exception e) {
               // tv.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "error"+al.get(i), Toast.LENGTH_SHORT).show();
                vidv.setVisibility(View.INVISIBLE);
            }
        }
    }
}