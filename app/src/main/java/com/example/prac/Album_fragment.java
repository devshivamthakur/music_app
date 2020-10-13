package com.example.prac;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static com.example.prac.MainActivity.album_array_list;

public class Album_fragment extends Fragment {
    RelativeLayout.LayoutParams layoutParams;
    RecyclerView recyclerView_album_fragment;

    public Album_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_album_fragment, container, false);
        recyclerView_album_fragment = v.findViewById(R.id.recycler_view_album_fragment);
        Album_adpater album_adpater = new Album_adpater(album_array_list, getContext());
        recyclerView_album_fragment.setAdapter(album_adpater);
        recyclerView_album_fragment.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return v;
    }
   /* public void onResume() {
        if (small_music.equals("yes")) {
            recyclerView_album_fragment.setLayoutParams(layoutParams);
            v1.setVisibility(View.VISIBLE);
        }
        if (check_media_play_or_pause) {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_pause_circle);
        } else {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_play_circle_);
        }
        super.onResume();
    }*/
}