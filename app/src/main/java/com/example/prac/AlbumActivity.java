package com.example.prac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumActivity extends AppCompatActivity {
    private TextView album_name;
    private ImageView album_img;
    private RecyclerView album_related_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        album_img = findViewById(R.id.album_activity_img);
        album_name = findViewById(R.id.album_name);
        album_related_recycler = findViewById(R.id.album_related_song_recycler);
    }
}