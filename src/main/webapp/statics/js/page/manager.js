

$(document).ready(function() {
    allInfo = default_get("/app/summary")
    console.log(allInfo)
    if (allInfo) {
        $("#ainfoListTbl").empty()
        var ser = [];
        var cat = [];
        var off = [];
        var on = [];
        var ser1 = []
        for (var i = 0; i < allInfo.length; i++) {
            var ai = allInfo[i]
            var elem = "<tr><td><a href='/page/forward?page=sceneAdd&appId=" + ai.app.appId + "'>" + ai.app.appName + "</a></td><td>" + ai.app.crtTime +
            "</td><td>" + ai.totalDev + "</td>" +
            (ai.onlineDev ? "<td><span class='badge badge-success'>" : "<td><span class='badge'>")
            + ai.onlineDev + "</span></td>";
            $("#ainfoListTbl").append(elem)
            ser.push([ai.app.appName, ai.totalDev])
            cat.push(ai.app.appName)
            on.push(ai.onlineDev)
            off.push(ai.totalDev - ai.onlineDev)
        }
        ser1.push({name: '离线', color: 'gray', data: off})
        ser1.push({name: '在线', color: 'green', data: on})
        console.log(cat)
        initPip("devRatio","各应用设备占比",ser,"")
        //console.log(ser)
        initStrip("onlineRatio", "各应用设备上线比",cat, ser1)
    }

});

function initChart(renderToId, ser, chartTitle) {
    Highcharts.setOptions({global: { useUTC: false}});
    if (ser.length > 0) {
        chart1 = new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: renderToId
                    },
                    tooltip:{
                        headerFormat: '<b>AppName: {series.name}</b><br>',
                        pointFormat: '{point.x: %Y-%m-%d %H:%M:%S}</b>'
                    },
                    credits:{
                        enabled: false
                    },

                    yAxis: {
                        title: {
                            text: '百分比'
                        },
                        labels: {
                            formatter: function() {
                                return this.value + '%';
                            }
                        },
                        min: 0
                    },

                    xAxis: {
                        type: 'datetime',
                        title: {
                            text: '时间'
                        },
                        labels: {
                            formatter: function () {
                                return Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',this.value);
                            }
                        },

                    },

                    size: {
                        width: 2000
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                enabled: true
                            },
                            dataLabels: {
                                formatter: function() {
                                    return this.y + '%';
                                },
                                enabled: true
                            }
                        },
                        series: {
                            animation: false
                        }
                    },
                    series: ser,
                    title: {
                        style: {
                            color: 'black',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: '22px',
                            fontWeight: 'bold'
                        },
                        text: chartTitle
                    }
                })
    }
}

function initPip(renderToId, t, d, unit) {
    $('#' + renderToId).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: t
        },
        credits:{
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}' + unit + '</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    },
                    connectorColor: 'silver'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '设备数',
            data: d
        }]
    });
}

function initStrip(renderToId, t, cat, ser) {
    $('#' + renderToId).highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: t
        },
        credits:{
            enabled: false
        },
        xAxis: {
            categories: cat,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '占比'
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent',
                maxPointWidth: 30
            }
        },
        series: ser
    });

}
function addSubmit() {
    alert("jjjjj")
    $("#addTenantForm").submit();
}

function addCancel() {
    history.go(-1)
}