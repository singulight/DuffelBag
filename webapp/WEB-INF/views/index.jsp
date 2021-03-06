<%@ page import="ru.singulight.duffelbag.nodes.AllNodes" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.singulight.duffelbag.nodes.SensorNode" %>
 <%--
  User: Grigorii Nizovoy info@singulight.ru
  Date: 02.09.16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru" ng-app="duffelbagAdmin">
<head>
    <base href="/home">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="resources/js/libs/angular.min.js"></script>
    <script src="resources/js/libs/angular-route.min.js"></script>
    <script src="resources/js/libs/angular-scenario.js"></script>
    <script src="resources/js/libs/angular-websocket.js"></script>
    <script src="resources/js/app.js"></script>
    <link href="resources/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/main.css" rel="stylesheet">

    <title>Панель администратора - Duffel Bag</title>
</head>

<body>
<div ng-controller="mainCtrl">
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <a class="navbar-brand" href="#/">Duffel Bag</a>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Главная</a></li>
                <li><a href="#">Установки</a></li>
                <li><a href="#">Действия</a></li>
                <li><a href="#">Помощь</a></li>
            </ul>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 sidebar">
                <ul class="nav nav-sidebar">
                    <li ng-class="{ active: isActive('/home')}"><a href="/home">Главная панель</a></li>
                    <li ng-class="{ active: isActive('/node')}"><a href="/node">Ноды</a></li>
                    <li ng-class="{ active: isActive('/locations')}"><a href="">Локации</a></li>
                    <li ng-class="{ active: isActive('/actives')}"><a href="">4444</a></li>
                    <li><a href="">5555</a></li>
                    <li><a href="">6666</a></li>
                </ul>
            </div>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div ng-view></div>
            </div>
        </div>
    </div>
</div>


</body>
</html>