'use strict';
angular.module('Dashboard', ['nvd3']);
angular.module('Dashboard').controller('DashboardController', ['$scope', '$http',
    function($scope, $http) {
        function getActiveCountries(start, end) {
            var result = new jinqJs()
                .from($scope.models.messages)
                .groupBy('originatingCountry')
                .count('total')
                .orderBy([{
                    field: 'total',
                    sort: 'desc'
                }])
                .select()
                .slice(start, (end + 1));
            return result;
        };

        function getActiveUsers(start, end) {
            var result = new jinqJs()
                .from($scope.models.messages)
                .groupBy('userId')
                .count('total')
                .orderBy([{
                    field: 'total',
                    sort: 'desc'
                }])
                .select()
                .slice(start, (end + 1));
            return result;
        };

        function getMostBought() {
            var result = new jinqJs()
                .from($scope.models.messages)
                .groupBy('currencyTo')
                .sum('amountBuy')
                .orderBy([{
                    field: 'amountBuy',
                    sort: 'desc'
                }])
                .select();
            return result;
        };

        function getMostSold() {
            var result = new jinqJs()
                .from($scope.models.messages)
                .groupBy('currencyFrom')
                .sum('amountSell')
                .orderBy([{
                    field: 'amountSell',
                    sort: 'desc'
                }])
                .select();
            return result;
        };

        $scope.init = function() {
            $scope.activeCountries = [];
            $scope.activeUsers = [];
            $scope.dataMostBought = [];
            $scope.dataMostSold = [];
            $scope.models = {
                searchText: '',
                messages: []
            };
            $scope.loadData();
        };

        $scope.messagesColumnDefinition = [{
            columnHeaderDisplayName: 'Currency from',
            displayProperty: 'currencyFrom',
            sortKey: 'currencyFrom',
            width: '9em',
            columnSearchProperty: 'currencyFrom'
        }, {
            columnHeaderTemplate: 'Currency to',
            displayProperty: 'currencyTo',
            sortKey: 'currencyTo',
            width: '9em',
            columnSearchProperty: 'currencyTo'
        }, {
            columnHeaderTemplate: 'Amount sell',
            displayProperty: 'amountSell',
            sortKey: 'amountSell',
            width: '9em'
        }, {
            columnHeaderDisplayName: 'Amount buy',
            displayProperty: 'amountBuy',
            sortKey: 'amountBuy',
            width: '9em'
        }, {
            columnHeaderDisplayName: 'Originating country',
            displayProperty: 'originatingCountry',
            sortKey: 'originatingCountry',
            width: '12em',
            columnSearchProperty: 'originatingCountry'
        }];

        $scope.loadData = function() {
            $http({
                method: 'GET',
                url: 'http://localhost:8888/messages'
            }).
            success(function(data, status, headers, config) {
                $scope.models.messages = data;
                if (data.length == 0)
                    $scope.error = 'No messages to show';
                else {
                    $scope.error = '';
                    $scope.activeCountries = getActiveCountries(0, 4);
                    $scope.activeUsers = getActiveUsers(0, 4);
                    $scope.dataMostBought = [{
                        key: "MostBought",
                        values: (getMostBought().length > 10) ? getMostBought().slice(0,10) : getMostBought()
                    }];
                    $scope.dataMostSold = [{
                        key: "MostSold",
                        values: (getMostSold().length > 10) ? getMostSold().slice(0,10) : getMostSold()
                    }];
                }
            }).
            error(function(data, status, headers, config) {
                $scope.error = "Sorry, but something went wrong."
            });
        };

        $scope.optionsActiveUsers = {
            chart: {
                type: 'pieChart',
                height: 500,
                x: function(d) {
                    return d.userId;
                },
                y: function(d) {
                    return d.total;
                },
                showLabels: true,
                transitionDuration: 500,
                labelThreshold: 0.01,
                legend: {
                    margin: {
                        top: 5,
                        right: 35,
                        bottom: 5,
                        left: 0
                    }
                },
                disabled: true,
                autorefresh: true
            }
        };

        $scope.optionsActiveCountries = {
            chart: {
                type: 'pieChart',
                height: 500,
                x: function(d) {
                    return d.originatingCountry;
                },
                y: function(d) {
                    return d.total;
                },
                showLabels: true,
                transitionDuration: 500,
                labelThreshold: 0.01,
                legend: {
                    margin: {
                        top: 5,
                        right: 35,
                        bottom: 5,
                        left: 0
                    }
                },
                disabled: true,
                autorefresh: true
            }
        };

        $scope.optionsMostBought = {
            chart: {
                type: 'discreteBarChart',
                height: 450,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 60,
                    left: 55
                },
                x: function(d) {
                    return d.currencyTo;
                },
                y: function(d) {
                    return d.amountBuy;
                },
                showValues: true,
                valueFormat: function(d) {
                    return d3.format(',.2f')(d);
                },
                transitionDuration: 500,
                xAxis: {
                    axisLabel: 'Currency To'
                },
                yAxis: {
                    axisLabel: 'Amount Buy',
                    axisLabelDistance: 30
                }
            }
        };

        $scope.optionsMostSold = {
            chart: {
                type: 'discreteBarChart',
                height: 450,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 60,
                    left: 55
                },
                x: function(d) {
                    return d.currencyFrom;
                },
                y: function(d) {
                    return d.amountSell;
                },
                showValues: true,
                valueFormat: function(d) {
                    return d3.format(',.2f')(d);
                },
                transitionDuration: 500,
                xAxis: {
                    axisLabel: 'Currency From'
                },
                yAxis: {
                    axisLabel: 'Amount Sell',
                    axisLabelDistance: 30
                }
            }
        };
    }
]);
