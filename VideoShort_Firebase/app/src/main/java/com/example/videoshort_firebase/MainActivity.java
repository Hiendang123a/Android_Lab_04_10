package com.example.videoshort_firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    VideoAdapter videoAdapter;
    List<VideoModel> list;
    VideosFireBaseAdapter videosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager2 = findViewById(R.id.vpager);
        list = new ArrayList<>();
        //getVideos1();
        getVideos2();
    }
    private void getVideos1(){
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference("videos");
        FirebaseRecyclerOptions<Video1Model> options = new FirebaseRecyclerOptions. Builder<Video1Model>()
                .setQuery(mDataBase, Video1Model.class).build();
        videosAdapter = new VideosFireBaseAdapter(options);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter);
    }
    private void getVideos2(){
        APIService.servieapi.getVideos().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<VideoModel>> call, Response<List<VideoModel>> response) {
                list = response.body();
                videoAdapter = new VideoAdapter(getApplicationContext(), list);
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                viewPager2.setAdapter(videoAdapter);
                videoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<VideoModel>> call, Throwable t) {
                Log.e("Lỗi Lỗi Lỗi", t.getMessage());
            }
        });
    }
    /*
    @Override
    protected void onStart() {
        super.onStart();
        videosAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        videosAdapter.stopListening();
    }
    @Override
    protected void onResume() {
        super.onResume();
        videosAdapter.notifyDataSetChanged();
    }

     */
}