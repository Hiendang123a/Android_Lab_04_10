package com.example.videoshort_firebase;

import java.util.List;

public class User {
    private String email;
    private String password;
    private String avatar;
    private List<VideoModel> videos;

    public User() {
        // Firebase yêu cầu constructor rỗng
    }

    public User(String email, String password, String avatar, List<VideoModel> videos) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.videos = videos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<VideoModel> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoModel> videos) {
        this.videos = videos;
    }
}
