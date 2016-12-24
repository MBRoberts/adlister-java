package com.codeup.adlister.models;

/**
 * Created by M.Ben_Roberts on 12/23/16.
 */
public class Category {
    private long id;
    private String category;

    public Category(long id, String category) {
        this.id = id;
        this.category = category;
    }

    public Category(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
