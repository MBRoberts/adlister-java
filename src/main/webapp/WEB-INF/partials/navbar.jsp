<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">

        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="/ads">Adlister</a>
        </div>

            <c:choose>
                <c:when test="${(sessionScope.user == null)}">

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/login">Login</a></li>
                        <li><a href="/register">Sign Up!</a></li>
                    </ul>

                </c:when>
                <c:otherwise>

                    <ul class="nav navbar-nav navbar-left">
                        <li><a href="/profile">Profile</a></li>
                        <li><a href="/profile/edit">Edit Profile</a></li>
                        <li><a href="/ads/create">Create Ad</a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/logout">Logout</a></li>
                    </ul>

                </c:otherwise>
            </c:choose>

    </div><!-- /.container-fluid -->
</nav>
