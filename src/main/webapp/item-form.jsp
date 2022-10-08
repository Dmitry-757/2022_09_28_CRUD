<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>Vinyla records list</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>



<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #4784ff">

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Vinyla records</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${item != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${item == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${item != null}">
                                Edit Record
                            </c:if>
                            <c:if test="${item == null}">
                                Add New Record
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${item != null}">
                        <input type="hidden" name="id" value="<c:out value='${item.id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Album</label> <input type="text" value="<c:out value='${item.name}' />" class="form-control" name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Author</label> <input type="text" value="<c:out value='${item.author}' />" class="form-control" name="author">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Year</label> <input type="text" value="<c:out value='${item.year}' />" class="form-control" name="year">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>

</html>