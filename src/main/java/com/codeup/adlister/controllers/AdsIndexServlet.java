package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.ocpsoft.prettytime.PrettyTime;

@WebServlet(name = "controllers.AdsIndexServlet", urlPatterns = "/ads")
public class AdsIndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Ad> ads = DaoFactory.getAdsDao().all();
        PrettyTime p = new PrettyTime();

        for (Ad ad : ads) {

            ad.setTimeCreatedAgo(p.format(ad.getCreatedAt()));
            ad.setTimeUpdatedAgo(p.format(ad.getUpdatedAt()));

            List<String> categories = DaoFactory.getCategoryDao().getCategoriesForAds(ad.getId());

            ad.setCategories(categories);

        }
        request.setAttribute("ads", ads);

        request.getRequestDispatcher("/WEB-INF/ads/index.jsp").forward(request, response);

        if(request.getSession().getAttribute("message") != null){

            request.getSession().removeAttribute("message");
        }
    }
}
