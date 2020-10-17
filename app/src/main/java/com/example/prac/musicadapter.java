package com.example.prac;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class musicadapter extends RecyclerView.Adapter<musicadapter.musicholder> {

    private ArrayList<musicfiles> songdata;
    private Context context;

    public musicadapter(ArrayList<musicfiles> songdata, Context context) {
        this.songdata = songdata;
        this.context = context;
    }

    @NonNull
    @Override

    public musicholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
        return new musicholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull musicholder holder, final int position) {
        holder.song_title.setText(songdata.get(position).getTitle());
        final byte[] img = getAlbumimg(songdata.get(position).getPath());
        if (img != null) {

            Glide.with(context).asBitmap().circleCrop().load(img).into(holder.song_img);
        } else {
            Glide.with(context).asBitmap().circleCrop().load(R.drawable.img2).into(holder.song_img);
        }
        holder.music_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, musicactivity.class);
                intent.putExtra("pos", position);
                intent.putExtra("from_where", "all_song");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songdata.size();
    }

    public static byte[] getAlbumimg(String uri) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(uri);
        byte[] img = mediaMetadataRetriever.getEmbeddedPicture();
        mediaMetadataRetriever.release();
        return img;
    }

    static class musicholder extends RecyclerView.ViewHolder {
        RelativeLayout music_item_layout;
        TextView song_title;
        ImageView song_img;

        public musicholder(@NonNull View itemView) {
            super(itemView);

            song_img = itemView.findViewById(R.id.music_album_img);
            song_title = itemView.findViewById(R.id.song_title);
            music_item_layout = itemView.findViewById(R.id.music_item_layout);
        }
    }
}
