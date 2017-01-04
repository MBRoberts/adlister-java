package com.codeup.adlister.controllers;

import com.codeup.adlister.dao.DaoFactory;
import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "controllers.CreateAdServlet", urlPatterns = "/ads/create")
@MultipartConfig

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        if(session.getAttribute("user") == null) {

            session.setAttribute("message", "Please Login First");
            response.sendRedirect("/login");
            return;

        }

        User user = (User) session.getAttribute("user");

        String title = null;
        String description = null;
        List categories = new ArrayList();
        String fileName = "no-image.png";
//
//        boolean inputsHaveErrors = title.isEmpty() || description.isEmpty();
//
//        if (inputsHaveErrors){
//
//            session.setAttribute("message", "Please fill out required fields");
//            response.sendRedirect("/ads/create");
//
//            return;
//
//        }

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);


        if (isMultipart) {

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {

                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();

                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if ( (!item.isFormField()) && (item.getSize() != 0) ) {

                        fileName = item.getName() ;

                        String root = getServletContext().getRealPath("/assets/img");
                        System.out.println("root: " + root);
                        File path = new File(root);

                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                            System.out.println("Status: " + status);
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        System.out.println("uploadedFile: " + uploadedFile.getAbsolutePath());

                        item.write(uploadedFile);

                    } else {

                        if ("title".equals(item.getFieldName())){
                            title = item.getString();
                        }

                        if ("description".equals(item.getFieldName())){
                            description = item.getString();
                        }

                        if ("categories".equals(item.getFieldName())){
                            categories.add(item.getString());
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Ad ad = new Ad(

            user.getId(),
            title,
            description,
            fileName

        );

        Long ad_id = DaoFactory.getAdsDao().insert(ad);

        if (!categories.isEmpty()) {

            for (Object category : categories) {

                Long cat_id = Long.parseLong(category.toString());
                DaoFactory.getCategoryDao().insert(ad_id, cat_id);

            }
        }

        session.setAttribute("message", "Ad created successfully");
        response.sendRedirect("/ads");

    }

}
