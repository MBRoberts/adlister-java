package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.prettytime.PrettyTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditAdServlet", urlPatterns = "/ads/edit")
public class EditAdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {

            request.getSession().setAttribute("message", "You must be logged in first!");
            response.sendRedirect("/login");
            return;

        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        // validate input
        boolean inputHasErrors = title.isEmpty() || description.isEmpty();

        if (inputHasErrors){
            response.sendRedirect("/profile/edit");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        Ad ad = new Ad(
            user.getId(),
            title,
            description,
            "no-image.png"
        );

        int updatedId = DaoFactory.getAdsDao().update(ad);

        if (updatedId > 0){
            request.getSession().setAttribute("message", "Update Successful");
            response.sendRedirect("/profile");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long adId = Long.parseLong(request.getParameter("ad"));
        Ad ad = DaoFactory.getAdsDao().getAdById(adId);
        PrettyTime p = new PrettyTime();

        ad.setTimeCreatedAgo(p.format(ad.getCreatedAt()));
        ad.setTimeUpdatedAgo(p.format(ad.getUpdatedAt()));

        List<String> categories = DaoFactory.getCategoryDao().getCategoriesForAds(ad.getId());

        ad.setCategories(categories);

        request.setAttribute("ad", ad);

        request.getRequestDispatcher("/WEB-INF/ads/edit.jsp").forward(request, response);
    }
}
