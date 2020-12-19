package com.example.prac;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class musicadapter extends RecyclerView.Adapter<musicadapter.musicholder> implements Filterable {

    private ArrayList<musicfiles> songdata;
    private ArrayList<musicfiles> C_songdata;      // copy of data
    private Context context;
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<musicfiles> filteredlist = new ArrayList<>();
            if (constraint == null && constraint.length() == 0) {
                filteredlist = C_songdata;
            } else {
                String txt = constraint.toString().toLowerCase();
                for (musicfiles mf : C_songdata) {
                    if ((mf.getTitle().toLowerCase()).contains(txt)) {
                        filteredlist.add(mf);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            songdata.clear();
            songdata.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override

    public musicholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
        return new musicholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull musicholder holder, final int position) {
        holder.song_title.setText(songdata.get(position).getTitle());
        holder.artist_name.setText(songdata.get(position).getArtist());
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
        try {
            mediaMetadataRetriever.setDataSource(uri);
        } catch (Exception e) {
        }
        byte[] img = mediaMetadataRetriever.getEmbeddedPicture();
        mediaMetadataRetriever.release();
        return img;
    }

    public musicadapter(ArrayList<musicfiles> songdata, Context context) {
        this.songdata = songdata;
        this.context = context;
        C_songdata = new ArrayList<>(songdata);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static class musicholder extends RecyclerView.ViewHolder {
        RelativeLayout music_item_layout;
        TextView song_title, artist_name;
        ImageView song_img;

        public musicholder(@NonNull View itemView) {
            super(itemView);

            song_img = itemView.findViewById(R.id.music_album_img);
            song_title = itemView.findViewById(R.id.song_title);
            music_item_layout = itemView.findViewById(R.id.music_item_layout);
            artist_name = itemView.findViewById(R.id.artist_name_from_music_item);
        }
    }
}
