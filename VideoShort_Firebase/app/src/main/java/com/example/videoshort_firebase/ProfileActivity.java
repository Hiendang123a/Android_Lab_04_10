package com.example.videoshort_firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextView emailTextView;
    ImageView avatarImageView, backButton;
    RecyclerView videosRecyclerView;
    VideoAdapter videoAdapter;
    ArrayList<VideoModel> videos = new ArrayList<>();
    Button upload;
    DatabaseReference userRef;
    FirebaseAuth auth;
    String avatarimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailTextView = findViewById(R.id.emailTextView);
        avatarImageView = findViewById(R.id.avatarImageView);
        videosRecyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.backButton); // Lấy nút quay lại
        upload = findViewById(R.id.upload);
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase
                .getInstance("https://videoshort-1f96c-default-rtdb.firebaseio.com/")
                .getReference("users");
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this, videos);
        videosRecyclerView.setAdapter(videoAdapter);

        // Thêm sự kiện cho nút quay lại
        backButton.setOnClickListener(v -> finish()); // Khi nhấn vào nút, Activity sẽ đóng và quay lại HomeActivity
        loadUserProfile();
        avatarImageView.setOnClickListener(v -> {
            Map config = new HashMap();
            config.put("cloud_name", "drskr95bw");
            config.put("api_key", "369548333411975");
            config.put("api_secret", "qQtAhNIR4Z1WYQ4nzp0HIFlN1Bc");
            MediaManager.init(this, config);
            openImageChooser();
        });

        upload.setOnClickListener(v->{
            startActivity(new Intent(ProfileActivity.this, UploadVideo.class));
        });
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(avatarImageView); // Hiển thị tạm thời ảnh

            uploadToCloudinary(imageUri);
        }
    }

    private void uploadToCloudinary(Uri uri) {
        MediaManager.get().upload(uri)
                .option("resource_type", "image")
                .callback(new com.cloudinary.android.callback.UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Toast.makeText(ProfileActivity.this, "Đang tải ảnh lên...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String imageUrl = resultData.get("secure_url").toString();
                        Glide.with(ProfileActivity.this).load(imageUrl).into(avatarImageView);
                        updateUserAvatar(imageUrl);
                    }

                    @Override
                    public void onError(String requestId, com.cloudinary.android.callback.ErrorInfo error) {
                        Toast.makeText(ProfileActivity.this, "Upload lỗi: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                        Log.e("CloudinaryUpload", "Upload lỗi chi tiết: " + error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, com.cloudinary.android.callback.ErrorInfo error) {}
                }).dispatch();
    }

    private void updateUserAvatar(String imageUrl) {
        String uid = auth.getCurrentUser().getUid();
        userRef.child(uid).child("avatar").setValue(imageUrl)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Đã cập nhật avatar!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi cập nhật avatar!", Toast.LENGTH_SHORT).show());
    }

    private void loadUserProfile() {
        String uid = auth.getCurrentUser().getUid();
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue(String.class);
                avatarimg = snapshot.child("avatar").getValue(String.class);
                emailTextView.setText(email);
                if(avatarimg != null && !avatarimg.isEmpty())
                    Glide.with(ProfileActivity.this).load(avatarimg).into(avatarImageView);

                // Load the user's videos
                for (DataSnapshot videoSnap : snapshot.child("videos").getChildren()) {
                    VideoModel video = videoSnap.getValue(VideoModel.class);
                    video.setEmail(email);
                    video.setAvatar(avatarimg);
                    videos.add(video);
                }
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}