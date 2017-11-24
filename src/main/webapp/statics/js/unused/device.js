var meta = null;
var monitor = null;
var ser = [];
var avg = [];

$(document).ready(function() {
    updatePageOnce()
    var timer1 = window.setInterval("updatePageOnce()", 10000)
    window.onbeforeunload = function(){window.clearInterval(timer1)};
    window.onunload = function(){window.clearInterval(timer1)};
});

function updatePageOnce() {
    getData()
    initAppMata()
    initDevices()
    initChart()
}

function getData() {
    var meta_url = "/app-device/getAppDevice"
    var monitor_url = "/monitor/getDevMonitor"
    var avg_url = "/monitor/getAvgMonitor"
    var d = {"appName": appName, "dmark": dmark}
    meta = default_get(meta_url, d)
    monitor = default_get(monitor_url, d)
    //console.log(meta)
    //console.log(monitor)
    ser.splice(0, ser.length)
    if (monitor.length > 0) {
        avg.splice(0, avg.length)
        for (var i = 0; i < monitor[0].portNum; i++) {
                d["port"] = i
                avg.push(default_get(avg_url, d))
        }
        console.log(monitor)
        for (var pi = 0; pi < monitor[0].portNum; pi++) {
            var name = ((meta.dports)[pi]).ddes
            var data = []
            for (var mi = 0; mi < monitor.length; mi++) {
                data.push([(monitor[mi]).dtimestamp, ((monitor[mi].values)[pi]).value])
            }
            ser.push({"name":name, "data": data})
        }
    }
    //console.log(avg)

}

function initAppMata() {
    $("#dmark").html(notNullElse(meta.dmark))
    $("#dtype").html(notNullElse(meta.dtype))
    $("#dcompany").html(notNullElse(meta.dcompany))
    $("#dlocation").html(notNullElse(meta.dlocX) + " , " + notNullElse(meta.dlocY))
    $("#ddes").html(notNullElse(meta.ddes))
}

function initDevices() {
    var lst_monitor = null
    var lst_dtime = null
    var lst_ptime = null
    if (monitor.length > 0) {
        lst_monitor = monitor[monitor.length - 1]
        lst_dtime = dateFormat(lst_monitor.dtimestamp)
        lst_ptime = dateFormat(lst_monitor.ptimestamp)

    }
    $("#port-list-title").html("Port list monitored at Dtime: "
                               + notNullElse(lst_dtime)
                               + "   Ptime: " + notNullElse(lst_ptime))
    $("tr").remove(".scss_value_tr")
    var portNum = meta.dportnum
    //console.log(portNum)
    for (var pi = 0; pi < portNum; pi++) {
        var idx = pi
        var ddes = notNullElse(((meta.dports)[pi]).ddes)
        var dvalue = lst_monitor ? ((lst_monitor.values)[pi]).value : ""
        var dvalid = lst_monitor ? ((lst_monitor.values)[pi]).valid : ""
        var dunit = notNullElse(((meta.dports)[pi]).dunit)
        var avgValue = avg[pi] ? (avg[pi]).avgValues : {}
        var t = []
        var v = []
        for (var e in avgValue) {
            t.push(e)
            v.push((avgValue[e].sumData　/　avgValue[e].sumNum).toFixed(2))
        }
        var item = "<tr class = 'scss_value_tr'><td class = 'scss_td'><a href = '" + modify(appName, dmark, pi) + "'> "
            + ddes + "</a></td><td class = 'scss_td'>" + dvalue + "</td><td class = 'scss_td'> " +
            dvalid  + "</td><td class = 'scss_td'> " + dunit + "</td>" +
            "<td class = 'scss_td'> " + aryToString(t, "</br>") + "</td><td class = 'scss_td'> " +
            aryToString(v, "</br>") + "</td></tr>"
        // console.log(item)
        $("#port-table").append(item)
    }
}

function initChart() {
    Highcharts.setOptions({global: { useUTC: false}});
    if (ser.length > 0) {
        chart1 = new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: "his-chart"
                    },
                    tooltip:{
                        headerFormat: '<b>{series.name}</b><br>',
                        pointFormat: '{point.x: %m-%d %H:%M:%S}: <b>{point.y}</b>'
                    },
                    credits:{
                        enabled: false
                    },

                    yAxis: {
                        title: {
                            text: '监测值'
                        },
                        min: 0
                    },

                    xAxis: {
                        type: 'datetime',
                        title: {
                            text: null
                        },
                        labels: {
                            formatter: function () {
                                return Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',this.value);
                            },
                            rotation:-30
                        },

                    },

                    size: {
                        width: 2000
                    },
                    plotOptions: {
                        spline: {
                            marker: {
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
                        text: "History Monitoring Value"
                    }
                })
    }
}

function back() {
    history.go(-1)
}

function del() {
    if(confirm("Del Device ?")) {
        var del_url = "/app-device/delAppDevice"
        var d = {"appName": appName, "dmark": dmark}
        formSubmit(del_url, d)
    }
}

function modify(appId, dmark, pi) {
    return "/page/forward?page=confEntry&caname=" + appId + "&cdmark=" + dmark + "&cpidx=" + pi
}