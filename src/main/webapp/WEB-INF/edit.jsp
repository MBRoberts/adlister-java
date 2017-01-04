<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Edit Your Profile" />
    </jsp:include>
</head>
<body>
    <jsp:include page="partials/navbar.jsp" />

    <div class="container">

        <h1>Please edit your information.</h1>

        <c:if test="${!sessionScope.message.equals(null)}">
            <div class="form-group has-error">${sessionScope.message}</div>
        </c:if>

        <form action="/profile/edit" method="post">

            <div class="form-group">
                <label for="username">Username</label>
                <input id="username" name="username" class="form-control" type="text" autofocus placeholder="Edit Username" value="${user.username}" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input id="email" name="email" class="form-control" type="text" placeholder="Edit Email" value="${user.email}" required>
            </div>


            <input type="submit" class="btn btn-primary btn-block">

        </form>
    </div>
</body>
</html>
