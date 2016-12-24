package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoriesDao implements Categories {

    private Connection connection = null;

    public MySQLCategoriesDao() {

        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection(
                    Config.url,
                    Config.user,
                    Config.password
            );

        } catch (SQLException e) {

            throw new RuntimeException("Error connecting to the database!", e);

        }
    }

    @Override
    public List<Category> all() {

        PreparedStatement stmt;

        try {

            stmt = connection.prepareStatement("SELECT * FROM categories");
            ResultSet rs = stmt.executeQuery();
            return createCategoriesFromResults(rs);

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving all categories.", e);

        }
    }

    @Override
    public Long insert(Long adId, Long categoryId) {

        try {

            String insertQuery = "INSERT INTO ad_category(ad_id, category_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, adId);
            stmt.setLong(2, categoryId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);

        } catch (SQLException e) {

            throw new RuntimeException("Error inserting a new category.", e);

        }
    }

    @Override
    public Category getCategoryById(Long categoryId) {

        PreparedStatement stmt;

        try {

            stmt = connection.prepareStatement("SELECT * FROM categories WHERE id IN (?)");
            stmt.setLong(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            return extractCategory(rs);

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving category by ID.", e);

        }
    }

    @Override
    public List<String> getCategoriesForAds(Long ad_id) {

        PreparedStatement stmt;
        List<String> categories = new ArrayList();

        try {

            stmt = connection.prepareStatement("SELECT c.category FROM categories as c JOIN ad_category AS a ON c.id = a.category_id WHERE a.ad_id = ?");
            stmt.setLong(1, ad_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                categories.add(rs.getString("category"));

            }

            return categories;

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving categories for ads.", e);

        }
    }

    private Category extractCategory(ResultSet rs) throws SQLException {

        return new Category(

                rs.getLong("id"),
                rs.getString("category")

        );
    }

    private List<Category> createCategoriesFromResults(ResultSet rs) throws SQLException {

        List<Category> categories = new ArrayList<>();

        while (rs.next()) {

            categories.add(extractCategory(rs));

        }

        return categories;
    }
}
