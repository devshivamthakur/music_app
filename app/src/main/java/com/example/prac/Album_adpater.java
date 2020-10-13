package com.example.prac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.prac.MainActivity.getPath;

public class Album_adpater extends RecyclerView.Adapter<Album_adpater.ViewHolder> {
    ArrayList<String> album_list;
    Context context;

    public Album_adpater(ArrayList<String> album_list, Context context) {
        this.album_list = album_list;
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String path;
        holder.album_name.setText(album_list.get(position));
        path = getPath(album_list.get(position));
        if (!path.isEmpty()) {
            byte[] img = musicadapter.getAlbumimg(path);
            if (img != null) {
                Glide.with(context).asBitmap().circleCrop().load(img).into(holder.album_img);
            } else {
                Glide.with(context).asBitmap().circleCrop().load(R.drawable.ic_launcher_background).into(holder.album_img);
            }

        }
    }

    @Override
    public int getItemCount() {
        return album_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView album_img;
        TextView album_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album_img = itemView.findViewById(R.id.album_fragment_img);
            album_name = itemView.findViewById(R.id.album_tittle);
        }
    }
}
