package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "editProfileServlet", urlPatterns = "/profile/edit")
public class editProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {

            request.getSession().setAttribute("message", "You must be logged in first!");
            response.sendRedirect("/login");
            return;

        }

        String username = Jsoup.clean(request.getParameter("username"), Whitelist.basic());
        String email = Jsoup.clean(request.getParameter("email"), Whitelist.basic());

        // validate input
        boolean inputHasErrors = username.isEmpty() || email.isEmpty();

        if (inputHasErrors){
            response.sendRedirect("/profile/edit");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        user.setUsername(username);
        user.setEmail(email);

        int updatedId = DaoFactory.getUsersDao().update(user);

        if (updatedId > 0){
            request.getSession().setAttribute("message", "Update Successful");
            response.sendRedirect("/profile");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {

            request.getSession().setAttribute("message", "You must be logged in first!");
            response.sendRedirect("/login");
            return;

        }

        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);

        if(request.getSession().getAttribute("message") != null){

            request.getSession().removeAttribute("message");
        }
    }
}
