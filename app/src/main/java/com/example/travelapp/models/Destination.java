package com.example.travelapp.models;

import java.io.Serializable;

public class Destination implements Serializable {
    private String name;
    private String location;
    private int imageResId;       // Ảnh tĩnh trong drawable
    private String imageUrl;      // Ảnh từ Firebase Storage / URL
    private String description;
    private boolean isFavorite;   // trạng thái yêu thích
    private double price;
    private double rating;
    private boolean available;

    // 🔹 Constructor mặc định (bắt buộc Firestore)
    public Destination() {}

    // 🔹 Constructor đầy đủ
    public Destination(String name, String location, int imageResId, String imageUrl,
                       String description, double price, double rating, boolean available) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.available = available;
        this.isFavorite = false;
    }

    // 🔹 Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getImageResId() { return imageResId; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return available; }
    public boolean isFavorite() { return isFavorite; }

    // 🔹 Setters
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setPrice(double price) { this.price = price; }
    public void setRating(double rating) { this.rating = rating; }
    public void setAvailable(boolean available) { this.available = available; }
}
