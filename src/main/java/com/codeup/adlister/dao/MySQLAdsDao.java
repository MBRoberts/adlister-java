package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import com.mysql.cj.jdbc.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAdsDao implements Ads {

    private Connection connection = null;

    public MySQLAdsDao() {

        try {

            DriverManager.registerDriver(new Driver());

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
    public List<Ad> all() {

        PreparedStatement stmt;

        try {

            stmt = connection.prepareStatement("SELECT * FROM ads");
            ResultSet rs = stmt.executeQuery();

            return createAdsFromResults(rs);

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving all ads.", e);

        }
    }

    @Override
    public List<Ad> getAdsByUserId(Long idNum) {

        PreparedStatement stmt;

        try {

            stmt = connection.prepareStatement("SELECT * FROM ads WHERE user_id = ?");
            stmt.setLong(1, idNum);
            ResultSet rs = stmt.executeQuery();

            return createAdsFromResults(rs);

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving all ads.", e);

        }
    }

    @Override
    public Ad getAdById(Long idNum) {

        PreparedStatement stmt;

        try {

            stmt = connection.prepareStatement("SELECT * FROM ads WHERE id = ? LIMIT 1");
            stmt.setLong(1, idNum);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return extractAd(rs);

        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving all ads.", e);

        }
    }

    @Override
    public Long insert(Ad ad) {

        try {

            String insertQuery = "INSERT INTO ads(user_id, title, description, photo_file_path) VALUES (?, ?, ? ,?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setLong(1, ad.getUserId());
            stmt.setString(2, ad.getTitle());
            stmt.setString(3, ad.getDescription());
            stmt.setString(4, ad.getPhotoFilePath());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();

            return rs.getLong(1);

        } catch (SQLException e) {

            throw new RuntimeException("Error creating a new ad.", e);

        }
    }

    private Ad extractAd(ResultSet rs) throws SQLException {

        return new Ad(

            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("photo_file_path"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")

        );
    }

    private List<Ad> createAdsFromResults(ResultSet rs) throws SQLException {

        List<Ad> ads = new ArrayList<>();

        while (rs.next()) { ads.add(extractAd(rs)); }

        return ads;
    }

    @Override
    public int update(Ad ad) {

        String query = "UPDATE ads SET title = ?, description = ?, photo_file_path = ? WHERE id = ?";

        try {

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ad.getTitle());
            stmt.setString(2, ad.getDescription());
            stmt.setString(3, ad.getPhotoFilePath());
            stmt.setLong(4, ad.getId());
            stmt.executeUpdate();

            int updateCount = stmt.getUpdateCount();

            return updateCount;

        } catch (SQLException e) {

            throw new RuntimeException("Error creating new user", e);

        }
    }
}
