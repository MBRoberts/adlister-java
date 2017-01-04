<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>
        <jsp:include page="/WEB-INF/partials/head.jsp">
            <jsp:param name="title" value="Viewing All The Ads" />
        </jsp:include>
    </head>

    <body>
        <jsp:include page="/WEB-INF/partials/navbar.jsp" />

        <div class="container">
            <h1>Here Are all the ads!</h1>

            <c:if test="${!sessionScope.message.equals(null)}">
                <div class="form-group has-error">${sessionScope.message}</div>
            </c:if>

            <c:forEach var="ad" items="${ads}">
                <div class="col-md-6">
                    <h2>${ad.title}</h2>
                    <img src="assets/img/${ad.photoFilePath}" height="300" width="300">
                    <p>Posted ${ad.timeCreatedAgo}</p>
                    <p>Posted by: ${ad.userCreated}</p>
                    <p>Updated Last ${ad.timeUpdatedAgo}</p>
                    <p>Description: ${ad.description}</p>
                    <p>Categories:
                        <c:forEach var="category" items="${ad.categories}">

                            ${category},

                        </c:forEach>
                    </p>
                </div>
            </c:forEach>
        </div>
    </body>
</html>
