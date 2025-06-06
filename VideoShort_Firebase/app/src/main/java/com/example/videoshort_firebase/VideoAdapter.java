package com.example.videoshort_firebase;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyHolder> {
    private Context context;
    private List<VideoModel> videoList;
    private boolean isFav = false;
    private boolean isDis = false;
    public VideoAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_video_row,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        VideoModel videoModel = videoList.get(i);
        holder.textVideoTitle.setText(videoModel.getTitle());
        holder.textVideoDescription.setText(videoModel.getDescription());
        holder.favcounts.setText(String.valueOf(videoModel.getLike()));
        holder.disfavcounts.setText(String.valueOf(videoModel.getDislike()));
        if(videoModel.getAvatar() == null || videoModel.getAvatar().isEmpty())
        {
            holder.imPerson.setImageResource(R.drawable.ic_person_pin);
        }
        else
            Glide.with(context).load(videoModel.getAvatar()).into(holder.imPerson);
        holder.tvEmail.setText(videoModel.getEmail());
        holder. videoView.setVideoURI(Uri.parse(videoModel.getVideoUrl()));
        holder. videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.videoProgressBar.setVisibility(View.GONE);
                mp.start();
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if (scale >= 1f) {
                    holder.videoView.setScaleX(scale);
                }
                else {
                    holder.videoView.setScaleY(1f / scale);
                }
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.videoView.setMediaController(new MediaController(context));
                holder.videoView.requestFocus();
                mediaPlayer.start();
            }
        });

        holder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFav){
                    holder.favorites.setImageResource(R.drawable.ic_fill_favotire);
                    isFav = true;
                }
                else
                {
                    holder.favorites.setImageResource(R.drawable.ic_favorite);
                    isFav = false;
                }
            }
        });

        holder.disfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDis){
                    holder.disfavorites.setImageResource(R.drawable.ic_disfavorite_fill);
                    isDis = true;
                }
                else
                {
                    holder.disfavorites.setImageResource(R.drawable.ic_disfavotire);
                    isDis = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(videoList != null)
            return videoList.size();
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ProgressBar videoProgressBar;
        TextView textVideoTitle;
        TextView textVideoDescription, favcounts, disfavcounts, tvEmail;
        ImageView imPerson, favorites, disfavorites, imShare, imMore, imAvatar;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
            disfavorites = itemView.findViewById(R.id.disfavorites);
            favcounts = itemView.findViewById(R.id.favcounts);
            disfavcounts = itemView.findViewById(R.id.disfavcounts);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
