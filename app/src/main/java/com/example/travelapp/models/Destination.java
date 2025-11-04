package com.example.travelapp.models;

public class Destination {
    private String name;
    private String location;
    private int imageResId;
    private String description;
    private boolean isFavorite; // ✅ thêm biến yêu thích

    // Constructor cũ (giữ nguyên để tương thích)
    public Destination(String name, String location, int imageResId) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.description = "";
        this.isFavorite = false; // mặc định chưa yêu thích
    }

    // ✅ Constructor mới có thêm mô tả
    public Destination(String name, String location, int imageResId, String description) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.description = description;
        this.isFavorite = false;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    // ✅ Setter cho trạng thái yêu thích
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
