var scenename = null
$(document).ready(function() {
    showList()
    var timer = null
    $("label.dot").hover(
     function () {
        var id = this.id
        timer = setTimeout(function(){
            var pid = id.replace("pointer", "")
            var pv = default_get("/monitor/pointer", {"pid":pid, "len":"1"})
            //console.log(pv)
            if (pv) {
                var pname = pv.p.pname
                var pvalue = "-"
                var pst = "-"
                var pvalid = "-"
                if (pv.values.length > 0) {
                    var value = (pv.values)[0]
                    pvalue = value.value.toFixed(2)  + pv.p.poutunit
                    pst = dateFormatByPattern(new Date(value.st), "hh:mm:ss");
                    pvalid = value.valid
                }
                $("#detail-pname").html("<b>设备名:&ensp;&ensp;</b>" + pname + "</span>")
                $("#detail-pvalue").html("<b>监测值:&ensp;&ensp;</b>" + pvalue + "</span>")
                $("#detail-ptime").html("<b>检测时间:&ensp;&ensp;</b>" + pst + "</span>")
                $("#detail-pvalie").html("<b>是否合法:&ensp;&ensp;</b>" + pvalid + "</span>")

                var cont = document.getElementById(id);
                $("#mytip").css("left", cont.offsetLeft + 30)
                $("#mytip").css("top", cont.offsetTop + 30)
                $("#mytip").show()
            } else {
                alert("获取实时值失败")
            }
        },800);
     },
     function () {
        if (timer) clearTimeout(timer);
        $("#mytip").hide()
     });

//        )(function() {
//
//        //alert(this.id)
//    });
//    $("label.dot").mouseleave(function() {
//        $("#mytip").hide()
//    });
});

function back() {
    history.go(-1)
}

function showList() {
    var url = "/app/scene"
    //alert(sid)
    var ps = default_get(url, {"sid": sid})
    //console.log(ps)
    if (ps) {
        if (ps.scene.sbgurl) {
            $("#appdetail-pane").attr("style","background:url('/images/user/" + ps.scene.sbgurl + "') no-repeat 50% 50%;");
        }
        scenename = ps.scene.sname
        for (var i = 0; i < ps.pointer.length; i++) {
            var pid = (ps.pointer[i]).pid
            var pname = (ps.pointer[i]).pname
            var pix = ((ps.dots)[pid]).x
            var piy = ((ps.dots)[pid]).y
            var elem = getDot("pointer" + pid, pname)
            //console.log(elem)
            //console.log(pix + "-:-" + piy)
            $("#appdetail-pane").append(elem)
            //console.log(document.getElementById("appdetail-pane"))
            $("#pointer" + pid).css("left", pix - 15)
            $("#pointer" + pid).css("top", piy - 15)
            ttt("pointer" + pid)
        }
    }
    var scenePV = default_get("/monitor/scene", {"sid":sid, "len":1})
    //console.log(scenePV)
    if (scenePV) {
        $("#devListTbl").empty()
        for (var key in scenePV) {
            var pv = scenePV[key]
            var pname = pv.p.pname
            var pvalue = "-"
            var pst = "-"
            var pvalid = "-"
            if (pv.values.length > 0) {
                var value = (pv.values)[0]
                pvalue = value.value.toFixed(2) + pv.p.poutunit
                pst = dateFormat(value.st, true);
                pvalid = value.valid
            }
            var elem = "<tr><td><a onclick = 'detail(" + pv.p.pid + ")'>" + pname + "</a></td><td>" + pvalue +
                       "</td><td>" + pst + "</td><td>" + pvalid + "</td><tr>";
            //console.log(elem)
            $("#devListTbl").append(elem)
        }

    }
    $("#title").html(scenename + " 场景详情")
}

function getDot(id, name) {
    var elem = "<label class = 'dot' id = '" + id + "'></label>"
    $("#" + id).mouseenter(function() {
        $("#hide" + id).show("normal");
    });
    $("#" + id).mouseleave(function() {
        $("#hide" + id).hide("normal");
    });
    return elem
}

function ttt(name) {
    document.getElementById(name).onclick = function(e) {
        var pid = this.id.replace("pointer", "")
        detail(pid)
    }
}
var ser = [];
var data = [];
function detail(i) {
    var info = default_get("/monitor/pointer", {"pid":i, "len":0})
    console.log(info)
    if (info) {
        $("#title1").html(info.p.pname + " 设备详情")
        $("#pname").html(info.p.pname)
		$("#poutdesc").html(notNullElse(info.p.poutdesc))
		$("#pouttype").html(notNullElse(info.p.pouttype))
		$("#poutunit").html(notNullElse(info.p.poutunit))
		$("#pdesc").html(notNullElse(info.p.pdesc))
		$("#pavg").html(info.p.pavg)
		$("#pavgtime").html(notNullElse(info.av.time))
		$("#pavgvalue").html(info.av.avg.toFixed(2))
		if (info.values.length > 0) {
		    var realV = ((info.values)[info.values.length - 1]).value
		    $("#prealvalue").html(realV.toFixed(2))
		    ser.splice(0, ser.length)
		    data.splice(0, data.length)
		    for (var r = 0; r < info.values.length; r++) {
		        realV = (info.values)[r]
		        data.push([realV.st, realV.value])
		    }
		    ser.push({"name":info.p.pname, "data":data})
		    initChart("realvaluehis", ser, info.p.pname + "历史数据")
		}
    }
    $("#detail").hide()
    $("#detail1").show()
    
}

function back1() {
    $("#detail1").hide()
    $("#detail").show()
}

function formatTime(st, t) {
    if (t == "NONE") {
        return "-"
    } else if (t == "MIN") {
        return dateFormatByPattern(st, "yyyy-MM-dd HH-mm")
    } else if (t == "HOUR") {
        return dateFormatByPattern(st, "yyyy-MM-dd HH")
    } else if (t == "DAY") {
        return dateFormatByPattern(st, "yyyy-MM-dd")
    } else {
        return dateFormat(st, true)
    }

}

function initChart(renderToId, ser, chartTitle) {
    Highcharts.setOptions({global: { useUTC: false}});
    if (ser.length > 0) {
        chart1 = new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: renderToId
                    },
                    tooltip:{
                        headerFormat: '<b>设备: </b>{series.name}<br>',
                        pointFormat: '<b>时间: </b>{point.x: %Y-%m-%d %H:%M:%S}'
                    },
                    credits:{
                        enabled: false
                    },

                    yAxis: {
                        title: {
                            text: '监测值'
                        },
                        labels: {
                            formatter: function() {
                                return Highcharts.numberFormat(this.value, 2);
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
                                    return Highcharts.numberFormat(this.y, 2);
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