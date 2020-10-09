package com.example.prac;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.prac.MainActivity.check_media_play_or_pause;
import static com.example.prac.MainActivity.musicfilesArrayList;

import static com.example.prac.MainActivity.next_small_layout_flag;
import static com.example.prac.MainActivity.small_music;
;
import static com.example.prac.musicactivity.img;
import static com.example.prac.musicactivity.listofsong;
import static com.example.prac.musicactivity.positon;

public class Songs_fragment extends Fragment {
    RecyclerView recyclerView;
    musicadapter music_Data_adapter;
    static ImageView imageView_next;
    static ImageView img_song_img;
    static TextView song_name2;
    View v1;
    RelativeLayout.LayoutParams layoutParams;
    static FloatingActionButton play_pause_btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_songs_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);
        if (musicfilesArrayList.size() > 1) {
            music_Data_adapter = new musicadapter(musicfilesArrayList, getContext());
            recyclerView.setAdapter(music_Data_adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            v1 = v.findViewById(R.id.small_music_layout);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 140);
            imageView_next = v.findViewById(R.id.id_next);
            img_song_img = v.findViewById(R.id.song_img2);
            song_name2 = v.findViewById(R.id.song_title2);
            play_pause_btn2 = v.findViewById(R.id.btn_play_pause2);
      /*  if(v1.getVisibility()==View.VISIBLE){

             recyclerView.setLayoutParams(layoutParams);
        }*/


        }
        return v;


    }

    @Override
    public void onResume() {
        if (small_music.equals("yes")) {
            recyclerView.setLayoutParams(layoutParams);
            v1.setVisibility(View.VISIBLE);
        }
        if (check_media_play_or_pause) {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_pause_circle);
        } else {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_play_circle_);
        }
        super.onResume();
    }


}