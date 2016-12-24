package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.User;
import com.codeup.adlister.util.Password;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {

            response.sendRedirect("/profile");
            return;

        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        if (request.getSession().getAttribute("message") != null){

            request.getSession().removeAttribute("message");

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = DaoFactory.getUsersDao().findByUsername(username);

        if (user == null) {

            request.getSession().setAttribute("message", "Please Try Again");
            response.sendRedirect("/login");

            return;

        }

        if (Password.check(password, user.getPassword())) {

            request.getSession().setAttribute("message", "Login Successful");
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/profile");

        } else {

            request.getSession().setAttribute("message", "Please Try Again");
            response.sendRedirect("/login");

        }
    }
}
