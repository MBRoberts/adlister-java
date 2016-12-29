package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import org.ocpsoft.prettytime.PrettyTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "controllers.ViewProfileServlet", urlPatterns = "/profile")
public class ViewProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Must be signed in first");
            response.sendRedirect("/login");
            return;

        }

        User user = (User) request.getSession().getAttribute("user");

        PrettyTime p = new PrettyTime();

        List<Ad> ads = DaoFactory.getAdsDao().getAdsByUserId(user.getId());

        for (Ad ad: ads) {

            List<String> categories = DaoFactory.getCategoryDao().getCategoriesForAds(ad.getId());
            ad.setCategories(categories);
            ad.setTimeCreatedAgo(p.format(ad.getCreatedAt()));
            ad.setTimeUpdatedAgo(p.format(ad.getUpdatedAt()));

        }

        request.setAttribute("ads", ads);
        request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);

        if(request.getSession().getAttribute("message") != null){

            request.getSession().removeAttribute("message");
        }
    }
}
