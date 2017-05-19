<%--
  User: Grigorii Nizovoy info@singulight.ru
  Date: 25.02.17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Панель администратора - Duffel Bag</title>
    <base href="/">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Polyfill(s) for older browsers -->
    <script src="resources/node_modules/core-js/client/shim.min.js"></script>
    <script src="resources/node_modules/zone.js/dist/zone.js"></script>
    <script src="resources/node_modules/systemjs/dist/system.src.js"></script>
    <script src="resources/systemjs.config.js"></script>

    <link href="resources/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/main.css" rel="stylesheet">
    <link href="resources/css/custom.css" rel="stylesheet">
    <script>
        System.import('app').catch(function(err){ console.error(err); });
    </script>
</head>
<body>
    <main-view>Loading...</main-view>
</body>
</html>
