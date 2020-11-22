package com.example.prac;

import android.content.Context;
import android.content.Intent;
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

import static com.example.prac.MainActivity.getPath;
import static com.example.prac.MainActivity.imageView_next;
import static com.example.prac.MainActivity.repeat_flag;

public class Album_adpater extends RecyclerView.Adapter<Album_adpater.ViewHolder> {
    ArrayList<String> album_list;     // it contains all album list
    Context context;
    byte[] img1;   // image of album

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String path;
        holder.album_name.setText(album_list.get(position));
        path = getPath(album_list.get(position));
        if (!path.isEmpty()) {
            byte[] img = musicadapter.getAlbumimg(path);
            img1 = img;
            if (img != null) {
                Glide.with(context).asBitmap().circleCrop().load(img).into(holder.album_img);
            } else {
                Glide.with(context).asBitmap().circleCrop().load(R.drawable.img2).into(holder.album_img);
            }

        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, AlbumActivity.class);
                it.putExtra("album_name", holder.album_name.getText().toString());
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return album_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView album_img;
        TextView album_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            album_img = itemView.findViewById(R.id.album_fragment_img);
            album_name = itemView.findViewById(R.id.album_tittle);
            relativeLayout = itemView.findViewById(R.id.relative_layout_for_album_adapter);
        }
    }
}
