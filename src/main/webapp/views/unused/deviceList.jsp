<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8"/>
        <link href = "/css/device.css" rel="stylesheet" type="text/css"/>
        <script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
    </head>

    <body>

        <div ng-app="myApp" ng-controller="tableCtrl">

           <table cellspacing = 0 cell-padding = 0 class = "device_table" ng-repeat = "(key,value) in map">
                <tr class = "device_title_tr">
                    <td colspan = "2" class = "device_td">{{key}}</td>
                </tr>

                <tr class = "device_title_tr">
                    <td class = "device_td">index</td>
                    <td class = "device_td">id</td>
                </tr>

                <tr class = "device_value_tr" ng-repeat = "x in value">
                    <td class = "device_td">{{$index + 1}}</td>
                    <td class = "device_td"><a href = "/device/detail?did={{x}}">{{x}}</td>
                </tr>

           </table>
        </div>

        <script>
            var app = angular.module('myApp', []);
            app.controller('tableCtrl', function($scope, $http, $interval) {
                $scope.loadData = function () {
                    $http.get("/device/getAllDevice")
                    .then(function (result) {
                        console.log(result)
                        $scope.map = result.data
                    });
                };
                $scope.loadData();

                var autoRefresh;
                autoRefresh = $interval($scope.loadData, 6000);
                $scope.stopAutoRefresh = function () {
                    if (autoRefresh) {
                        $interval.cancel(autoRefresh);
                        autoRefresh = null;
                    }
                }
                $scope.$on('$destroy', function() {
                    $scope.stopAutoRefresh();
                });
            });
        </script>
    </body>
</html>
