/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.11.16.
 */


var app = angular.module('duffelbagAdmin', ['ngRoute','ngWebSocket']);

app.config(function($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
        .when('/home', {
            templateUrl: 'resources/templates/home.html',
            controller: 'HomeCtrl'
        })
        .when('/node', {
            templateUrl: 'resources/templates/node.html',
            controller: 'NodeCtrl'
        })
        .when('/locations', {
            templateUrl: 'resources/templates/locations.html',
            controller: 'locationsCtrl'
        })
        .otherwise({redirectTo: '/home'});
});

app.factory('WebSocketData',  ['$rootScope', function ($rootScope) {
    var Service = {};
    cbMsg = {"type":0};
    var ws = new WebSocket('ws://localhost:8080');
    ws.onopen = function () {
        console.log("Socket succsessfully created");
    };
    ws.onmessage = function (msg, cbMsg) {
        listener(JSON.parse(msg.data));
    };

    function listener (data) {
        cbMsg = data;
        $rootScope.$broadcast('MsgReceived',data);
    }

    Service.getMessage = function () {
        return cbMsg;
    };
        
    return Service;
}]);

app.controller('mainCtrl', ['$scope', '$location', function($scope, $location) {
    $scope.isActive = function(viewLocation) {
        return $location.path().indexOf(viewLocation) == 0;
    }

}]);

app.controller('HomeCtrl', ['$rootScope', '$scope', 'WebSocketData', function($rootScope, $scope, WebSocketData) {
    $scope.homepage = "Главная";
    $scope.$on('MsgReceived', function (event, data) {
        console.log(data);
        $scope.jsond = data;
        $scope.$apply();
    })

}]);

app.controller('NodeCtrl', ['$scope', function($scope) {
    $scope.homepage = "Ноды";
}]);

/* App model */
var nodes = [];
