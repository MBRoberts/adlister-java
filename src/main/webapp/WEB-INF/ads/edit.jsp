<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Edit Ad" />
    </jsp:include>
</head>
<body>

<jsp:include page="/WEB-INF/partials/navbar.jsp" />

<div class="container">
    <h1>Edit Ad</h1>

    <c:if test="${!sessionScope.message.equals(null)}">
        <div class="form-group has-error">${sessionScope.message}</div>
    </c:if>

    <form action="/ads/edit" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label for="title">Title</label>
            <input id="title" name="title" class="form-control" type="text" value="${ad.title}" autofocus required placeholder="Title">
        </div>

        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description" class="form-control" type="text">${ad.description}</textarea>
        </div>

        <div class="form-group">
            <label for="photo">Photo</label>
            <input id="photo" name="photo" type="file" value="${ad.photoFilePath}">
        </div>

        <div class="form-group">
            <c:forEach var="category" items="${categories}">
                <input type="checkbox" id="category${category.id}" class="categories" name="categories" value="${category.id}">
                <label for="category${category.id}">${category.category}</label>
            </c:forEach>
        </div>

        <input type="hidden" name="id" value="${ad.id}">
        <input type="submit" class="btn btn-block btn-primary">

    </form>
</div>
</body>
</html>
