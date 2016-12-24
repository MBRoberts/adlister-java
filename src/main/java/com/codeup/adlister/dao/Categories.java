package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;

import java.util.List;

/**
 * Created by M.Ben_Roberts on 12/23/16.
 */
public interface Categories {

    List<Category> all();

    Long insert(Long adId, Long categoryId);

    Category getCategoryById(Long id);

    List<String> getCategoriesForAds(Long ad_id);
}
