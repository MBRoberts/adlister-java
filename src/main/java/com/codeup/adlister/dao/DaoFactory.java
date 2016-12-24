package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;

public class DaoFactory {
    private static Ads adsDao;
    private static Users usersDao;
    private static Categories categoriesDao;

    public static Ads getAdsDao() {
        if (adsDao == null) {
            adsDao = new MySQLAdsDao();
        }
        return adsDao;
    }

    public static Users getUsersDao() {
        if (usersDao == null) {
            usersDao = new MySQLUsersDao();
        }
        return usersDao;
    }

    public static Categories getCategoryDao() {
        if (categoriesDao == null) {
            categoriesDao = new MySQLCategoriesDao();
        }
        return categoriesDao;
    }
}
