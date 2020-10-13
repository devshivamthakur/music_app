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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.prac.MainActivity.check_media_play_or_pause;
import static com.example.prac.MainActivity.musicfilesArrayList;
import static com.example.prac.MainActivity.small_music;

public class Songs_fragment extends Fragment {
    RecyclerView recyclerView;
    musicadapter music_Data_adapter;


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
      /*  if(v1.getVisibility()==View.VISIBLE){

             recyclerView.setLayoutParams(layoutParams);
        }*/


        }
        return v;


    }

    @Override
    public void onResume() {

        super.onResume();
    }


}