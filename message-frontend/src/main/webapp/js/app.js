'use strict';

angular.module('Authentication', []);
angular.module('Dashboard', []);

angular.module('MarketTradeProcessor', [
    'Authentication',
    'Dashboard',
    'ngRoute',
    'ngCookies',
    'ngSanitize',
    'adaptv.adaptStrap'
])

.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'views/login.html'
        })

    .when('/', {
        controller: 'DashboardController',
        templateUrl: 'views/dashboard.html'
    })

    .otherwise({
        redirectTo: '/login'
    });

    $httpProvider.defaults.headers.common["Accept"] = "application/json";
    $httpProvider.defaults.headers.common["Content-Type"] = "application/json";
    $httpProvider.interceptors.push(function($q) {
        return {
            responseError: function(rejection) {
                if (rejection.status == 0) {
                    window.location = "views/error.html";
                    return;
                }
                return $q.reject(rejection);
            }
        };
    });

}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function($rootScope, $location, $cookieStore, $http) {
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }
        $rootScope.$on('$locationChangeStart', function(event, next, current) {
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
    }
]);
