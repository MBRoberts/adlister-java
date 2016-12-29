package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "controllers.CreateAdServlet", urlPatterns = "/ads/create")
public class CreateAdServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if(session.getAttribute("user") == null) {

            session.setAttribute("message", "Please Login First");
            response.sendRedirect("/login");
            return;
        }

        request.setAttribute("categories", DaoFactory.getCategoryDao().all());
        request.getRequestDispatcher("/WEB-INF/ads/create.jsp").forward(request, response);

        if(session.getAttribute("message") != null){

            session.removeAttribute("message");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        if(session.getAttribute("user") == null) {

            session.setAttribute("message", "Please Login First");
            response.sendRedirect("/login");
            return;

        }

        User user = (User) session.getAttribute("user");
        String title = Jsoup.clean(request.getParameter("title"), Whitelist.basic());
        String description = Jsoup.clean(request.getParameter("description"), Whitelist.basic());
        String[] categories = request.getParameterValues("categories");

        boolean inputsHaveErrors = title.isEmpty() || description.isEmpty();

        if (inputsHaveErrors){

            session.setAttribute("message", "Please fill out required fields");
            response.sendRedirect("/ads/create");

            return;

        }

        Ad ad = new Ad(

            user.getId(), // for now we'll hardcode the user id
            request.getParameter("title"),
            request.getParameter("description")

        );

        Long ad_id = DaoFactory.getAdsDao().insert(ad);

        if (categories != null) {

            for (String category : categories) {

                Long cat_id = Long.parseLong(category);
                DaoFactory.getCategoryDao().insert(ad_id, cat_id);

            }
        }

        response.sendRedirect("/ads");

    }
}
