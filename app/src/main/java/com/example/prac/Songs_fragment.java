package com.example.prac;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import static com.example.prac.MainActivity.musicfilesArrayList;

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

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu,menu);
//        MenuItem search_menu_item=menu.findItem(R.id.search_menu);
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public void onResume() {

        super.onResume();
    }

    /*
     * todo: this method used to get data from mainActivity's searched text and make filter
     *  */
    public void onSearchFileter(String newtext) {
        Log.e("searched1", newtext);
        music_Data_adapter.getFilter().filter(newtext);
    }
}