package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;
import java.util.List;

public interface Categories {

    List<Category> all();

    Long insert(Long adId, Long categoryId);

    Category getCategoryById(Long id);

    List<String> getCategoriesForAds(Long ad_id);
}
