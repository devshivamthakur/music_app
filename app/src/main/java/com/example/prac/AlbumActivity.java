package com.example.prac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.prac.MainActivity.getPath;
import static com.example.prac.MainActivity.musicfilesArrayList;

public class AlbumActivity extends AppCompatActivity {
    private TextView album_name;
    private ImageView album_img;
    private RecyclerView album_related_recycler;
    String album_name1;
    byte[] img;
    ArrayList<musicfiles> music_from_album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        album_img = findViewById(R.id.album_activity_img);
        album_name = findViewById(R.id.album_name);
        getIntentdata();
        setdetails();
        album_related_recycler = findViewById(R.id.album_related_song_recycler);
        music_from_album = getdata();
        album_related_song_adapter adapter = new album_related_song_adapter(music_from_album, getApplicationContext());
        album_related_recycler.setAdapter(adapter);
        album_related_recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getIntentdata() {
        Intent it = getIntent();
        album_name1 = it.getStringExtra("album_name");
        String path = getPath(album_name1);
        Log.e("path", path);
        img = musicadapter.getAlbumimg(path);
    }

    private void setdetails() {
        if (album_name1 != null && img != null) {
            album_name.setText(album_name1);
            Glide.with(getApplicationContext()).asBitmap().load(img).into(album_img);
        }
        if (img == null) {
            Glide.with(getApplicationContext()).asBitmap().load(R.drawable.img2).into(album_img);
        }
    }

    private ArrayList<musicfiles> getdata() {  // it return all song file that relate with album nama
        ArrayList<musicfiles> temp = new ArrayList<>();
        if (album_name1 != null) {
            for (musicfiles m : musicfilesArrayList) {
                if (m.getAlbum().equals(album_name1)) {
                    temp.add(m);
                }
            }
        }
        return temp;
    }
}