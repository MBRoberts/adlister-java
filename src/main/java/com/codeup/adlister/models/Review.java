package com.codeup.adlister.models;

/**
 * Created by M.Ben_Roberts on 12/22/16.
 */
public class Review {
    private long id;
    private long userId;
    private long adId;
    private String reviewText;

    public Review(long id, long userId, long adId, String reviewText) {
        this.id = id;
        this.userId = userId;
        this.adId = adId;
        this.reviewText = reviewText;
    }

    public Review(long userId, long adId, String reviewText) {
        this.userId = userId;
        this.adId = adId;
        this.reviewText = reviewText;
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

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public String getComment() {
        return reviewText;
    }

    public void setComment(String reviewText) {
        this.reviewText = reviewText;
    }
}
