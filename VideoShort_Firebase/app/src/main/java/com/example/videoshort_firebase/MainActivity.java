package com.example.videoshort_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    ImageView currentAvatar;

    DatabaseReference mDataBase;
    FirebaseAuth auth;
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
        auth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("users");
        videoAdapter = new VideoAdapter(this, list);
        viewPager2.setAdapter(videoAdapter);
        currentAvatar = findViewById(R.id.imAvatar);
        getAllVideo();
        getCurrentUserAvatar();
        currentAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        //getVideos2();
    }

    private void getAllVideo(){
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    String email = userSnap.child("email").getValue(String.class);
                    String avatar = userSnap.child("avatar").getValue(String.class);
                    for (DataSnapshot videoSnap : userSnap.child("videos").getChildren()) {
                        VideoModel video = videoSnap.getValue(VideoModel.class);
                        video.setEmail(email);
                        video.setAvatar(avatar);
                        list.add(video);
                    }
                }
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void getCurrentUserAvatar() {
        String uid = auth.getCurrentUser().getUid();
        mDataBase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String avatar = snapshot.child("avatar").getValue(String.class);
                if(avatar != null && !avatar.isEmpty())
                    Glide.with(MainActivity.this).load(avatar).into(currentAvatar);
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
    /*
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
     */
}