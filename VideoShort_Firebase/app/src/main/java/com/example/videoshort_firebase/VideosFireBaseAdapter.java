package com.example.videoshort_firebase;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<Video1Model,VideosFireBaseAdapter.MyHolder> {
    boolean isFav = false;

    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_video_row,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int i, @NonNull Video1Model model) {
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());
        //holder.videoView.setVideoPath(videoModel.getUrl());
        holder. videoView.setVideoURI(Uri.parse(model.getUrl()));
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
    }
    public class MyHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        ProgressBar videoProgressBar;
        TextView textVideoTitle;
        TextView textVideoDescription;
        ImageView imPerson, favorites, imShare, imMore;

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
        }

        public VideoView getVideoView() {
            return videoView;
        }

        public void setVideoView(VideoView videoView) {
            this.videoView = videoView;
        }

        public ProgressBar getVideoProgressBar() {
            return videoProgressBar;
        }

        public void setVideoProgressBar(ProgressBar videoProgressBar) {
            this.videoProgressBar = videoProgressBar;
        }

        public TextView getTextVideoTitle() {
            return textVideoTitle;
        }

        public void setTextVideoTitle(TextView textVideoTitle) {
            this.textVideoTitle = textVideoTitle;
        }

        public TextView getTextVideoDescription() {
            return textVideoDescription;
        }

        public void setTextVideoDescription(TextView textVideoDescription) {
            this.textVideoDescription = textVideoDescription;
        }

        public ImageView getImPerson() {
            return imPerson;
        }

        public void setImPerson(ImageView imPerson) {
            this.imPerson = imPerson;
        }

        public ImageView getFavorites() {
            return favorites;
        }

        public void setFavorites(ImageView favorites) {
            this.favorites = favorites;
        }

        public ImageView getImShare() {
            return imShare;
        }

        public void setImShare(ImageView imShare) {
            this.imShare = imShare;
        }

        public ImageView getImMore() {
            return imMore;
        }

        public void setImMore(ImageView imMore) {
            this.imMore = imMore;
        }
    }
}
