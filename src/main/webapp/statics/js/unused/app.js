var meta = null;
var monitor = null;
var sers = {};
var didToPortsMap = null;

$(document).ready(function() {
    updatePageOnce()

    $("a.guanbi").click(function() {
        popunhide();
    });

    $(window).resize(function() {
        var _top = ($(window).height() - $(".popup").height()) / 2;
        var _left = ($(window).width() - $(".popup").width()) / 2;
        $(".popup").css({
            top : _top,
            left : _left
        });
    });


    var timer1 = window.setInterval("updatePageOnce()", 10000)
    window.onbeforeunload = function(){window.clearInterval(timer1)};
    window.onunload = function(){window.clearInterval(timer1)};
});

function updatePageOnce() {
    getData()
    initAppMata()
    initDevices()
    initShows()
}

function getData() {
    var meta_url = "/app/getAppDetailInfo"
    var monitor_url = "/monitor/getDevsMonitor"
    var d = {"appId": appId}
    meta = default_get(meta_url, d)
    didToPortsMap = {}
    for (var i = 0; i < (meta.devices).length; i++) {
        var key = ((meta.devices)[i]).did
        var value = ((meta.devices)[i]).dports
        didToPortsMap[key] = value
    }
    monitor = default_get(monitor_url, d)
    console.log(meta)
    //console.log(monitor)
    var shows = meta.shows
    //console.log(didToPortsMap)
    for (var si = 0; si < shows.length; si++) {
        var show = shows[si]
        //console.log("show" + show.sid)
        var sinfo = sers["show" + show.sid]
        if (!sinfo) {
            sinfo = {"desc": show.sdesc, "ser": []}
            sers["show" + show.sid] = sinfo
        }
        //console.log(show)
        var ser = sinfo.ser
        ser.splice(0, ser.length)
        var tmpMonitor = show.sdids
        for (var i = 0; i < tmpMonitor.length; i++) {
            var m = monitor[tmpMonitor[i]]
            var pi = (show.sports)[i]
            var name = ((didToPortsMap[tmpMonitor[i]])[pi]).ddes
            var data = []
            for (var mi = 0; mi < m.length; mi++) {
                data.push([m[mi].dtimestamp, ((m[mi].values)[pi]).value])
            }
            ser.push({"name": name, "data": data})
        }
    }
    //console.log(sers)
}

function initAppMata() {
    $("#appName").html(meta.app.appName)
    $("#acompany").html(notNullElse(meta.app.acompany))
    $("#adesc").html(notNullElse(meta.app.adesc))
    $("#acrttime").html(notNullElse(meta.app.crtTime))
}

function initDevices() {
    var devices = meta.devices
    $("tr").remove(".scss_value_tr")
    for (var i = 0; i < devices.length; i++) {
        var idx = i + 1
        var dmark = devices[i].dmark ? devices[i].dmark : "-"
        var dtype = devices[i].dtype ? devices[i].dtype : "-"
        var dcompany = devices[i].dcompany ? devices[i].dcompany : "-"
        var dlocX = devices[i].dlocX ? devices[i].dlocX : "-"
        var dlocY = devices[i].dlocY ? devices[i].dlocY : "-"
        var ddes = devices[i].ddes ? devices[i].ddes : "-"
        var item = "<tr class = 'scss_value_tr'><td class = 'scss_td'> "
        + idx + "</td><td class = 'scss_td'><a href = '/page/device?appName=" + meta.app.appName + "&dmark=" + dmark + "'>" + dmark + "</a></td><td class = 'scss_td'> " +
        dtype  + "</td><td class = 'scss_td'> " + dcompany + "</td><td class = 'scss_td'>"
         + '(' + dlocX + ',' + dlocY + ')' + "</td><td class = 'scss_td'>" + ddes + "</td></tr>"
        $("#app-table").append(item)
    }
}

function initShows() {
    var shows = meta.shows
    //console.log(shows)
    for (var i = 0; i < shows.length; i++) {
        initChart(shows[i].sid);
    }
}

function initChart(sid) {
    var chartId = "chart" + sid;
    $("#shows").append("<div id = '" + chartId + "' class = 'div_chart'></div>")
    Highcharts.setOptions({global: { useUTC: false}});
    var ser = sers["show" + sid].ser
    var title = sers["show" + sid].desc
    if (ser.length > 0) {
        chart1 = new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: chartId
                    },
                    tooltip:{
                        headerFormat: '<b>{series.name}</b><br>',
                        pointFormat: '{point.x: %H:%M:%S}: <b>{point.y}</b>'
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
                    series: sers["show" + sid].ser,
                    title: {
                        style: {
                            color: 'black',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: '22px',
                            fontWeight: 'bold'
                        },
                        text: sers["show" + sid].desc
                    }
                })
    }
}

function back() {
    history.go(-1)
}

function del() {
    if(confirm("Del App ?")) {
        var del_url = "/app/removeApp"
        var d = {"appId": appId}
        formSubmit(del_url, d)
    }
}

function addDevice() {
    var appName = meta.app.appName
    var url = "/page/forward?page=addDevice&appName=" + appName
    window.location = url
}

function addShow() {
    var appName = meta.app.appName
    var url = "/page/forward?page=addShow&appName=" + appName
    window.location = url
}

function popunshow() {
	$("#gray").show();
	$("#popup").show();
	var _top = ($(window).height() - $(".popup").height()) / 2;
    var _left = ($(window).width() - $(".popup").width()) / 2;
    console.log(_top + "," + _left)
    $(".popup").css({
        top : _top,
        left : _left
    });
    $("#show-name").empty()
    console.log(meta.shows)
    for (var i = 0; i < meta.shows.length; i++) {
        var s = (meta.shows)[i]
        var v = s.sid
        var t = s.sdesc
        $("#show-name").append("<option value='" + v+ "'>" + t + "</option>")
    }
}

function popunhide() {
	$("#gray").hide();
	$("#popup").hide();
	//document.getElementById("back").focus();
}

function delShow() {
    if(confirm("Del Show ?")) {
        var sid = $("#show-name option:selected").val()
        formSubmit("/app-show/delAppShow", {"appId": appId, "sid": sid})
    }
}