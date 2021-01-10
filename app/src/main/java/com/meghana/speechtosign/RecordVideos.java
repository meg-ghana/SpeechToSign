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
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RecordVideos extends Fragment {
    String umessage = "";
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
        umessage = ((RecordingActivity) getActivity()).message;

        umessage = umessage.trim().replace("'", "");
        String[] arr = umessage.split(" ");
        String new_ms = "";
        al = new ArrayList<String>();
        if (umessage.contains(" ")) {
            String s = "and the is am are or to a an";
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals("")) {
                    if (arr[i].equals("dont")) {
                        al.add("dont-" + arr[i + 1]);
                        i += 1;
                    } else if (arr[i].equalsIgnoreCase("I")) {
                        al.add("me");
                    } else if (s.contains(arr[i])) {
                        Toast.makeText(getActivity(), "doesnt work: " + arr[i], Toast.LENGTH_LONG).show();
                    } else {
                        al.add(arr[i]);
                    }
                }
            }
            umessage = al.get(0);
        } else {
            al.add(umessage);
        }
        for (String s : al) {
            System.out.println("heyyeyey" + s);
        }

        System.out.println(umessage);
        scroll = v.findViewById(R.id.sv);
        linlay = v.findViewById(R.id.linlay);
        ScrollView.LayoutParams lay2 = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linlay.setLayoutParams(lay2);
        // tv = v.findViewById(R.id.textv);
        // name = v.findViewById(R.id.names);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < al.size(); i++) {
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lay.setMargins(100, 40, 100, 0);

            TextView tv2 = new TextView(getActivity());
            tv2.setTextColor(Color.BLACK);
            tv2.setTextSize(30f);
            tv2.setText(al.get(i).replace("-", " "));
            if (al.get(i).equals("me")) {
                tv2.setText("me/I");
            }
            tv2.setGravity(Gravity.CENTER_HORIZONTAL);

            CardView cv = new CardView(getActivity());
            cv.setRadius(10);
            cv.setLayoutParams(lay);
            //cv.addView(tv);
            //linlay.addView(cv);

            VideoView vidv = new VideoView(getActivity());
            //vidv.setLayoutParams(lay);
            CardView cv2 = new CardView(getActivity());
//            LinearLayout newLL = new LinearLayout(getActivity());
//            newLL.setOrientation(LinearLayout.VERTICAL);
//            CardView.LayoutParams cardlay = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            newLL.setLayoutParams(cardlay);
//            lay.setMargins(100,40,100,0);
            cv2.setRadius(10);
            cv2.setLayoutParams(lay);
            // tv.setLayoutParams(lay);
            //newLL.addView(tv);
            //newLL.addView(vidv);
            //cv2.addView(newLL);
            TextView tv = new TextView(getActivity());
            tv.setText("");
            TextView tvv = new TextView(getActivity());
            tvv.setTextColor(Color.BLACK);
            tvv.setTextSize(30f);
            tvv.setText(al.get(i) + "not found");
            try {

                //  name.setText(umessage);
                Uri video = Uri.parse("https://media.signbsl.com/videos/asl/startasl/mp4/" + al.get(i) + ".mp4");
                RequestQueue rq = Volley.newRequestQueue(getActivity());
                RequestFuture<JSONObject> ft = RequestFuture.newFuture();
                String url = "https://media.signbsl.com/videos/asl/startasl/mp4/" + al.get(i) + ".mp4";
                System.out.println(al.get(i)+"fjhkrjf");
                StringRequest response1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hey1!!");
                        linlay.addView(tv2);
                        cv2.addView(vidv);
                        cv2.addView(tv);
                        linlay.addView(cv2);
                        MediaController mc = new MediaController(getActivity());
                        mc.setAnchorView(vidv);
                        vidv.setMediaController(mc);
                        vidv.setVideoURI(video);
                        if (video == null)
                            System.out.println("nononono");
                        vidv.start();
                        vidv.pause();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ooosppspsps"+error.toString());
                    }
                });
                rq.add(response1);
                    /*StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("hey"+response.charAt(0));
                            linlay.addView(tv2);
                            cv2.addView(vidv);
                            cv2.addView(tv);
                            linlay.addView(cv2);
                            MediaController mc = new MediaController(getActivity());
                            mc.setAnchorView(vidv);
                            vidv.setMediaController(mc);
                            vidv.setVideoURI(video);
                            if(video==null)
                                System.out.println("nononono");
                            vidv.start();
                            vidv.pause();
                            System.out.println("response");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(getActivity(), "not found: "+m, Toast.LENGTH_SHORT).show();

                        }
                    });*/

            // Toast.makeText(getActivity(), "work"+al.get(i), Toast.LENGTH_LONG).show();
        } catch(Exception e){
            // tv.setVisibility(View.VISIBLE);
            System.out.println("yikes");
        }

    }
}
}