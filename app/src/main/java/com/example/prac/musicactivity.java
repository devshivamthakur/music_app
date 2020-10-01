package com.example.prac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.prac.MainActivity.musicfilesArrayList;

public class musicactivity extends AppCompatActivity {
    static byte[] img = null;
    static ArrayList<musicfiles> listofsong = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    TextView song_name, artist_name, duration_played, duration_total, current_playing;
    ImageView back_btn, menu_btn, song_img, suffle_btn, repeate_btn, btn_prev, btn_next;
    FloatingActionButton btn_play_pause;
    SeekBar seekBar;
    int positon = -1;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicactivity);
        initview();
        getintentdata();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);     // for millisecond   /// load into song song run according to the seekbar, set timing
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        musicactivity.this.runOnUiThread(new Runnable() {    // for song multi thread
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    song_details();
                    int current_pos = mediaPlayer.getCurrentPosition() / 1000; // to get in second
                    seekBar.setProgress(current_pos);      // set progress in seek bar
                    duration_played.setText(formaattedTime(current_pos));

                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void song_details() {
        duration_total.setText(formaattedTime(mediaPlayer.getDuration() / 1000));
        current_playing.setText(listofsong.get(positon).getTitle());
        song_name.setText(listofsong.get(positon).getTitle());
        artist_name.setText(listofsong.get(positon).getArtist());

    }

    private String formaattedTime(int current_pos) {             // formatted time convert into second and minute;
        String totalout = "";
        String totalnew = "";
        String second = String.valueOf(current_pos % 60); // get in second
        String minute = String.valueOf(current_pos / 60); // get in menute
        totalout = minute + ":" + second;
        totalnew = minute + ":" + "0" + second;
        if (second.length() == 1) {
            return totalnew;
        }
        return totalout;
    }

    private void getintentdata() {
        Intent intent = getIntent();
        positon = intent.getIntExtra("pos", -1);
        img = intent.getByteArrayExtra("song_img");
        listofsong = musicfilesArrayList;
        if (listofsong != null) {
            btn_play_pause.setImageResource(R.drawable.ic_baseline_pause);
            uri = Uri.parse(listofsong.get(positon).getPath());
            if (img != null) {

                Glide.with(getApplicationContext()).asBitmap().load(img).into(song_img);
            } else {
                Glide.with(getApplicationContext()).asBitmap().load(R.drawable.ic_launcher_background).into(song_img);
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);         // take it as second

    }

    private void initview() {
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.duration_played);
        duration_total = findViewById(R.id.duration_total);
        current_playing = findViewById(R.id.txt_current_playing);
        back_btn = findViewById(R.id.back_btn);
        menu_btn = findViewById(R.id.menu_btn);
        song_img = findViewById(R.id.cover_art);
        suffle_btn = findViewById(R.id.id_shuffle_btn);
        repeate_btn = findViewById(R.id.id_repeat_btn);
        btn_next = findViewById(R.id.id_next);
        btn_prev = findViewById(R.id.id_prev);
        btn_play_pause = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seek_bar);
    }
}