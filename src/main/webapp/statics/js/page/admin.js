var allInfo = null;
var cpuSer = [];
var memSer = [];
var ioSer = [];
var netSer = [];

var msgTotalNum = 0;
var msgTotalBytes = 0;
var msgTotalNumT = 0;
var msgTotalBytesT = 0;

var msgNumSer = []
var msgBytesSer = []

$(document).ready(function() {
    allInfo = default_get("/cluster/statistic")
    //[{
    //    name: "192.168.1.111",
    //    data: [40,42,42.5,50,50.0,70.9]
    //}
    console.log(allInfo)
    if (allInfo) {
        cpuSer = cpuSer.splice(0, cpuSer.length)
        memSer = cpuSer.splice(0, cpuSer.length)
        ioSer = cpuSer.splice(0, cpuSer.length)
        netSer = cpuSer.splice(0, cpuSer.length)
        msgNumSer = msgNumSer.splice(0, msgNumSer.length)
        msgBytesSer = msgBytesSer.splice(0, msgBytesSer.length)
        msgTotalNum = 0
        msgTotalBytes = 0
        for (var key in allInfo) {
            var list = allInfo[key]
            var cpuData = []
            var memData = []
            var ioData = []
            var netData = []
            for (var i = 0; i < list.length; i++) {
                var elem = list[i]
                if (i == list.length - 1) {
                    msgTotalNum += elem.msgNum
                    msgTotalBytes += elem.msgBytes
                    msgTotalNumT += elem.msgNumT
                    msgTotalBytesT += elem.msgBytesT
                    msgNumSer.push([key, elem.msgNumT])
                    msgBytesSer.push([key, elem.msgBytesT])
                }
                cpuData.push([elem.time, elem.cpu])
                memData.push([elem.time, elem.mem])
                ioData.push([elem.time, elem.io])
                netData.push([elem.time, elem.net])
            }
            cpuSer.push({"name": key, "data":cpuData})
            memSer.push({"name": key, "data":memData})
            ioSer.push({"name": key, "data":ioData})
            netSer.push({"name": key, "data":netData})
        }
        console.log(msgTotalNum + ":" + msgTotalBytes)
        initChart("cpu", cpuSer, "CPU Usage")
        initChart("mem", memSer, "MEM Usage")
        initChart("io", ioSer, "IO Usage")
        initChart("net", netSer, "NET Usage")
        $("#msgNum").html(msgTotalNum)
        $("#msgBytes").html(bFormat(msgTotalBytes))
        $("#msgNumToday").html(msgTotalNumT)
        $("#msgBytesToday").html(bFormat(msgTotalBytesT))
        initPip("pip1", "今日消息数量占比", msgNumSer, "个")
        console.log(msgNumSer)
        initPip("pip2", "今日消息字节占比", msgBytesSer, "字节")
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
                        headerFormat: '<b>IP: {series.name}</b><br>',
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
                                return this.value * 100 + '%';
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
                                    return this.y * 100 + '%';
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
            name: '实际量',
            data: d
        }]
    });
}

function addSubmit() {
    alert("jjjjj")
    $("#addTenantForm").submit();
}

function addCancel() {
    history.go(-1)
}