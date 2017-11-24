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

});

function skipToPage(page) {
    //console.log("xxxxxxxxxxxxxxxx")
    getSceneList(page - 1)
}

function getSceneList(page) {
    var url = "/app/summary-scene"
    var slist = default_get(url, {"page": page})
    console.log(slist)
    if (slist) {
        $("#ainfoListTbl").empty()
        for (var i = 0; i < slist.length; i++) {
            var ai = slist[i]
            var elem = "<tr><td><a href='/page/forward?page=userSceneDetail&sid=" + ai.scene.sid + "'>" + ai.scene.sname +
            "</a></td><td><a href='/page/forward?page=userAppScene&appId=" + ai.scene.said + "'>" + ai.scene.appname +
            "</a></td><td>" + ai.totalDev +
            (ai.onlineDev ? "</td><td><span class='badge badge-success'>" : "</td><td><span class='badge'>")
            + ai.onlineDev + "</span></td>";
            $("#ainfoListTbl").append(elem)
        }
    }
}