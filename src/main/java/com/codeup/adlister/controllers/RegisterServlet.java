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

@WebServlet(name = "controllers.RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);

        if (request.getSession().getAttribute("message") != null){

            request.getSession().removeAttribute("message");

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = Jsoup.clean(request.getParameter("username"), Whitelist.basic());
        String email = Jsoup.clean(request.getParameter("email"), Whitelist.basic());
        String password = Jsoup.clean(request.getParameter("password"), Whitelist.basic());
        String passwordConfirmation = Jsoup.clean(request.getParameter("confirm_password"), Whitelist.basic());

        // validate input
        boolean inputHasErrors = username.isEmpty()
            || email.isEmpty()
            || password.isEmpty()
            || (! password.equals(passwordConfirmation));

        if (inputHasErrors) {

            request.getSession().setAttribute("message", "Please fill out required fields");
            response.sendRedirect("/register");
            return;

        }

        // create and save a new user
        User user = new User(username, email, password);
        user.setId(DaoFactory.getUsersDao().insert(user));

        request.getSession().setAttribute("user", user);
        response.sendRedirect("/profile");
    }
}
