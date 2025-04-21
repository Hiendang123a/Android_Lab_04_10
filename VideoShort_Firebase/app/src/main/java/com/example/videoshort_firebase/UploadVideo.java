package com.example.videoshort_firebase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UploadVideo extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST = 1;

    private VideoView videoView;
    private EditText etTitle, etDescription;
    private Button btnUpload, btnSave;

    private Uri videoUri;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference videosRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        initCloudinary();
        initFirebase();

        btnUpload.setOnClickListener(v -> chooseVideo());
        btnSave.setOnClickListener(v -> uploadVideoToFirebase());
    }

    private void initUI() {
        videoView = findViewById(R.id.videoPreview);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        videosRef = FirebaseDatabase.getInstance("https://videoshort-1f96c-default-rtdb.firebaseio.com/")
                .getReference("users");
    }

    private void chooseVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();

            try {
                // Dùng Uri trực tiếp để phát video
                videoView.setVideoURI(videoUri);
                Log.d("URIVIDEO",videoUri.toString());
                videoView.requestFocus();
                videoView.setOnPreparedListener(mp -> {
                    videoView.start(); // Tự động play sau khi load xong
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Không thể phát video, thử lại với video khác.", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void initCloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "drskr95bw");
        config.put("api_key", "369548333411975");
        config.put("api_secret", "qQtAhNIR4Z1WYQ4nzp0HIFlN1Bc");
        MediaManager.init(this, config);
    }

    private void uploadVideoToFirebase() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (videoUri == null || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin và chọn video", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> options = new HashMap<>();
        options.put("resource_type", "video"); // Chỉ định đây là video
        // Upload lên Cloudinary
        MediaManager.get().upload(videoUri).options(options)
                .callback(new UploadCallback() {
                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String videoUrl = resultData.get("secure_url").toString();

                        String uid = firebaseAuth.getCurrentUser().getUid();

                        // Tạo video mới
                        VideoModel newVideo = new VideoModel(title, description, videoUrl, 0, 0);

                        // Lấy videoId ngẫu nhiên
                        String videoId = FirebaseDatabase.getInstance()
                                .getReference()
                                .push()
                                .getKey();

                        // Lưu video vào "users/{uid}/videos/{videoId}"
                        videosRef.child(uid).child("videos").child(videoId).setValue(newVideo)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(UploadVideo.this, "Tải lên thành công!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UploadVideo.this,ProfileActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(UploadVideo.this, "Lỗi khi lưu video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(UploadVideo.this, "Upload lỗi: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStart(String requestId) {}
                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}
                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {}
                }).dispatch(UploadVideo.this);
    }
}