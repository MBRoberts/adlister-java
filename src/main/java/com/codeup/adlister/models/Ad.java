package com.codeup.adlister.models;

import java.sql.Timestamp;
import java.util.List;

public class Ad {

    private long id;
    private long userId;
    private String userCreated;
    private String title;
    private String description;
    private String timeCreatedAgo;
    private String timeUpdatedAgo;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<String> categories;
    private String photoFilePath;

    public Ad(long id, long userId, String title, String description, String filePath, Timestamp createdAt, Timestamp updatedAt) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.photoFilePath = filePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public Ad(long userId, String title, String description, String filePath) {

        this.userId = userId;
        this.title = title;
        this.description = description;
        this.photoFilePath = filePath;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getCategories() { return categories; }

    public void setCategories(List<String> categories) { this.categories = categories; }

    public String getTimeCreatedAgo() {
        return timeCreatedAgo;
    }

    public void setTimeCreatedAgo(String timeCreatedAgo) {
        this.timeCreatedAgo = timeCreatedAgo;
    }

    public String getTimeUpdatedAgo() {
        return timeUpdatedAgo;
    }

    public void setTimeUpdatedAgo(String timeUpdatedAgo) {
        this.timeUpdatedAgo = timeUpdatedAgo;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
}
