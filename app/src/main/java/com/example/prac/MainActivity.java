package com.example.prac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.example.prac.musicactivity.mediaPlayer;
import static com.example.prac.musicactivity.positon;


public class MainActivity extends AppCompatActivity {
    public static final int REQUESTCODE = 1;
    public static ArrayList<musicfiles> musicfilesArrayList;
    public static ArrayList<String> album_array_list = new ArrayList<>();
    static boolean shuffle_flag;
    static boolean repeat_flag;
    static boolean next_small_layout_flag;
    static String small_music = "no";
    static boolean check_media_play_or_pause;
    static ImageView imageView_next;
    static ImageView img_song_img;
    static TextView song_name2;
    static FloatingActionButton play_pause_btn2;
    View v1;
    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static String getPath(String album_name) {
        for (musicfiles f1 : musicfilesArrayList) {
            if (f1.getAlbum().equals(album_name)) {
                return f1.getPath();
            }
        }
        return "";
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
        } else {
            Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            musicfilesArrayList = getallaudio(getApplicationContext());
            initViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUESTCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                musicfilesArrayList = getallaudio(getApplicationContext());
                initViewPager();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);

            }
        }
    }

    private void initViewPager() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.View_pager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Songs_fragment(), "Song");
        viewPageAdapter.addFragment(new Album_fragment(), "Album");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 140);
        imageView_next = findViewById(R.id.id_next);
        img_song_img = findViewById(R.id.song_img2);
        song_name2 = findViewById(R.id.song_title2);
        relativeLayout = findViewById(R.id.tab_layout_Relative);
        v1 = findViewById(R.id.small_music_layout);

        play_pause_btn2 = findViewById(R.id.btn_play_pause2);
    }

    public ArrayList<musicfiles> getallaudio(Context context) {
        ArrayList<musicfiles> templist = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String order = MediaStore.MediaColumns.TITLE + "";
        String projection[] = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,       // song name
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA      // for path
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, order);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(4);
                String title = cursor.getString(2);
                String album = cursor.getString(0);
                String artist = cursor.getString(1);
                String duration = cursor.getString(3);
                musicfiles musicfiles = new musicfiles(path, title, album, duration, artist);
                try {
                    templist.add(musicfiles);
                } catch (Exception e) {

                }
                if (!album_array_list.contains(album)) {
                    album_array_list.add(album);
                }
            }
        }
        return templist;
    }

    @Override
    protected void onResume() {                    // use if music is playing and press back btn
        if (small_music.equals("yes")) {
            relativeLayout.setLayoutParams(layoutParams);
            v1.setVisibility(View.VISIBLE);
        }
        if (check_media_play_or_pause) {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_pause_circle);
        } else {
            play_pause_btn2.setImageResource(R.drawable.ic_baseline_play_circle_);
        }
        go_on_music_activity();
        super.onResume();
    }

    private void go_on_music_activity() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                img_song_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dura = mediaPlayer.getCurrentPosition();
                        Intent it = new Intent(MainActivity.this, musicactivity.class);
                        it.putExtra("from_where", "small_music_act");
                        it.putExtra("pos", positon);
//                        Toast.makeText(MainActivity.this, String.valueOf(positon), Toast.LENGTH_SHORT).show();
                        it.putExtra("duration", dura);
                        startActivity(it);
                    }
                });
            }
        };
        t1.start();
    }

    public static class ViewPageAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments;
        ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

}