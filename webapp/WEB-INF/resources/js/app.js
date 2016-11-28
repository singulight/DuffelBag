/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.11.16.
 */


var app = angular.module('duffelbagAdmin', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'resources/templates/home.html',
            controller: 'Home'
        })
        .when('/node', {
            templateUrl: 'resources/templates/node.html',
            controller: 'Node'
        })
        .otherwise({redirectTo: '/'});
});

app.controller('Home', ['$scope', function($scope) {
    $scope.homepage = "Главная";
}]);

app.controller('Node', ['$scope', function($scope) {
    $scope.homepage = "Ноды";
}]);

/*
WebSocket callback
 */
var websocket = new WebSocket('ws://localhost:8080');

websocket.onopen = function() {
};

websocket.onclose = function() {

};