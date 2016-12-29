<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <jsp:include page="/WEB-INF/partials/head.jsp">
            <jsp:param name="title" value="Your Profile" />
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/partials/navbar.jsp" />

        <div class="container">
            <h1>Welcome, ${sessionScope.user.username}!</h1>
        </div>

        <c:forEach var="ad" items="${ads}">
            <div class="col-md-6">
                <h2>${ad.title}</h2>
                <p>Posted ${ad.timeCreatedAgo}</p>
                <p>Updated Last ${ad.timeUpdatedAgo}</p>
                <p>Description: ${ad.description}</p>
                <p>Categories:
                    <c:forEach var="category" items="${ad.categories}">

                        ${category},

                    </c:forEach>
                </p>
            </div>
        </c:forEach>

    </body>
</html>
