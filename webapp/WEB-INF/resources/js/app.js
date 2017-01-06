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
    
    return Service;
}]);


app.controller('mainCtrl', ['$scope', '$location', 'WebSocketData', function($scope, $location) {
    $scope.isActive = function(viewLocation) {
        return $location.path().indexOf(viewLocation) == 0;
    };

    $scope.$on('MsgReceived', function (event, data) {

        switch (data.page) {
            case 'root':
                break;
            case 'home':
                $scope.$broadcast('MsgHome', data);
                break;
            case 'node':
                $scope.$broadcast('MsgNoge' ,data);
                break;
        }
    });

}]);

app.controller('HomeCtrl', ['$scope', function($scope) {
    $scope.homepage = "Главная";
    $scope.$on('MsgHome', function (event, data) {
        console.log(data);
        $scope.jsond = data.data;
        $scope.$apply();
    })

}]);

app.controller('NodeCtrl', ['$scope', function($scope) {
    $scope.homepage = "Ноды";
}]);

/* App model */
var nodes = [];
