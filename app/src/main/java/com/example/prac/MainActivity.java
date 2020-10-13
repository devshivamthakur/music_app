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
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUESTCODE = 1;
    public static ArrayList<musicfiles> musicfilesArrayList;
    public static ArrayList<String> album_array_list = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
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
    ViewPager.MarginLayoutParams v;

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
        //String order=MediaStore.Audio.Media.DISPLAY_NAME+" asc ";
        String projection[] = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA      // for path
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(4);
                String title = cursor.getString(2);
                String album = cursor.getString(0);
                String artist = cursor.getString(1);
                String duration = cursor.getString(3);
                musicfiles musicfiles = new musicfiles(path, title, album, duration, artist);
                if (title.equals("champ_install")) {

                } else {
                    templist.add(musicfiles);
                }
                if (!album_array_list.contains(album)) {
                    album_array_list.add(album);
                }
            }
        }
        return templist;
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
    protected void onResume() {
        if (small_music.equals("yes")) {
            relativeLayout.setLayoutParams(layoutParams);
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