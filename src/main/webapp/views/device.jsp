<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8"/>
        <link href = "/css/device.css" rel="stylesheet" type="text/css"/>
        <script src="http://cdn.static.runoob.com/libs/angular.js/1.4.6/angular.min.js"></script>
        <script src="http://cdn.static.runoob.com/libs/jquery/2.1.4/jquery.min.js"></script>
        <script src="/js/highcharts.js"></script>
        <script src="/js/highcharts-ng.js"></script>
        <title>Detail information of device</title>
    </head>

    <body>
        <div ng-app="myApp" ng-controller="tableCtrl">
            <div class = "device_div"><b>Device id:</b> {{infos.id}}</div>
            <div class = "device_div"><b>Device last time:</b> {{infos.dtimestamp | date: 'yyyy-MM-dd HH:mm:ss'}}</div>
            <div class = "device_div"><b>Platform last time:</b> {{infos.ptimestamp | date: 'yyyy-MM-dd HH:mm:ss'}}</div>
            <div class = "device_div"><b>Device monitor type:</b> {{infos.mtype}}</div>
            <div class = "device_div"><b>Device port num:</b> {{infos.portnum}}</div>
            <div class = "device_div"><b>Device desc:</b> {{infos.desc}}</div>
            <table cellspacing = 0 cell-padding = 0 class = "device_table">
                <tr class = "device_title_tr">
                    <td class = "device_td">portidx</td>
                    <td class = "device_td">value</td>
                    <td class = "device_td">unit</td>
                    <td class = "device_td">valid</td>
                    <td class = "device_td">config</td>
                    <td class = "device_td">avg</td>
                </tr>
                <tr class = "device_value_tr" ng-repeat="x in infos.values">
                    <td class = "device_td">{{$index + 1}}</td>
                    <td class = "device_td">{{x.value}}</td>
                    <td class = "device_td">{{x.unit}}</td>
                    <td class = "device_td">{{x.valid}}</td>
                    <td class = "device_td">{{piCmd[$index] ? piCmd[$index] : piCmd[-1]}}</td>
                    <td class = "device_td"><a href = "">{{piAvg[$index] ? piAvg[$index] : piAvg[-1]}}</a></td>
                </tr>
            </table>

            <div id = "div_chart">
                <highchart class = "chart-container-big" title='charts.title' series="charts.series" options="charts.options">
                </highchart>
            </div>
        </div>

        <script>
            Highcharts.setOptions({ global: { useUTC: false } });
            var myApp = angular.module('myApp', ["highcharts-ng"])
            var time = []
            var dataShow = []
            myApp.controller('tableCtrl', function($scope, $http, $interval) {
                 $scope.loadData = function () {
                 $http.get("/deviceConfig/getAllDconfig?did=${did}")
                    .then(function (result) {
                        var piCmd = {}
                        var piAvg = {}
                        var rd = result.data
                        for (var idx = 0; idx < rd.length; idx ++) {
                            piCmd[rd[idx].pidx] = rd[idx].cmd;
                            piAvg[rd[idx].pidx] = rd[idx].avg;
                        }
                        $scope.piCmd = piCmd;
                        $scope.piAvg = piAvg;
                        console.log(piCmd);
                        console.log(piAvg);
                    });

                 $http.get("/device/getDevice?did=${did}")
                    .then(function (result) {
                        var retData = result.data
                        time.length = 0;
                        //dataShow.length = 0;
                        if (retData.length < 1) return
                        var last = JSON.parse(retData[retData.length - 1])
                        var portnum = last.portnum
                        if (dataShow.length == 0) {
                            for (var idx = 0; idx < portnum; idx++) {
                                dataShow.push({
                                    "name" : "port" + (idx + 1),
                                    "data" : []
                                });
                            }
                        } else {
                            for (var idx = 0; idx < portnum; idx++) {
                                dataShow[idx].data.length = 0;
                            }
                        }
                        retData.forEach(function(elem) {
                            var obj = JSON.parse(elem);
                            time.push(Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', '' + obj.ptimestamp));
                            var vs = obj.values;
                            for (var idx = 0; idx < portnum; idx++) {
                                dataShow[idx].data.push(vs[idx].value)
                            }
                        })
                        //console.log(dataShow)
                        $scope.infos = last

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

                 $scope.charts = {
                      options: {
                          chart: {
                               type: 'line',
                               zoomType: 'x',
                               renderTo: "div_chart"
                          },

                          tooltip: {
                              xDateFormat: '%Y-%m-%d %H:%M:%S',
                              valueDecimals: 2
                          },
                          credits:{
                            enabled: false
                          },
                          yAxis: {
                            title: {
                                text: null
                            }
                          },
                          xAxis: {
                              type: 'datetime',
                              dateTimeLabelFormats: {
                                  hour: '%H:%M'
                              },
                              labels: {
                                rotation:-30
                              },
                              categories: time
                          },
                          size: {
                            width: 2000
                          },
                          plotOptions: {
                            series: {
                                animation: false
                            }
                          }
                      },
                      series: dataShow,
                      title: {
                        text: "Values of Device"
                      }
                 }
            });
        </script>
    </body>
</html>
