package com.example.videoshort_firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VideoModel implements Serializable {
    String title;
    String description;
    String videoUrl;
    int like;
    int dislike;
    // THÊM: Dữ liệu phụ để gắn vào mỗi video từ người dùng
    private String email;
    private String avatar;
    public VideoModel() {
    }

    public VideoModel(String title, String description, String videoUrl, int like, int dislike) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.like = like;
        this.dislike = dislike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static List<VideoModel> getSampleVideoList() {
        List<VideoModel> videoList = new ArrayList<>();

        videoList.add(new VideoModel("Lời thì thầm lúc nửa đêm", "Bom tấn kinh dị", "https://cdn.pixabay.com/video/2020/04/01/34730-403408248_tiny.mp4", 10, 2));
        videoList.add(new VideoModel("Vượt ngục thành Rome", "Hành động gay cấn", "https://cdn.pixabay.com/video/2022/10/04/133501-756991137_tiny.mp4", 20, 5));
        videoList.add(new VideoModel("Ký ức mùa đông", "Tình cảm - tâm lý", "https://cdn.pixabay.com/video/2024/07/17/221590_large.mp4", 15, 1));
        videoList.add(new VideoModel("Thế giới máy móc", "Viễn tưởng tương lai", "https://cdn.pixabay.com/video/2024/01/21/197658-905360129_tiny.mp4", 5, 0));
        videoList.add(new VideoModel("Sát thủ trong bóng tối", "Hành động - rượt đuổi", "https://cdn.pixabay.com/video/2020/02/17/32471-392669575_tiny.mp4", 50, 10));

        return videoList;
    }
    public static List<VideoModel> getTwoRandomVideos() {
        List<VideoModel> sampleList = getSampleVideoList();
        Collections.shuffle(sampleList); // Trộn ngẫu nhiên danh sách
        return sampleList.subList(0, 2); // Trả về 2 video đầu tiên sau khi trộn
    }
}
