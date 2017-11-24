var appname = null
$(document).ready(function() {
    showList()
});

function back() {
    history.go(-1)
}

function showList() {
    var url = "/app/scene-list"
    //alert(appId)
    var appinfo = default_get(url, {"appId": appId})
    if (appinfo) {
        console.log(appinfo)
        $("#applist-ul").empty()
        for(var i = 0; i < appinfo.length; i++) {
            var ai = appinfo[i]
            if (ai.appname && !appname) appname = ai.appname
            var elem = "<li class='span3'><div class='thumbnail' onclick = 'to(" + ai.sid + ")'>" +
            			"<img class = 'img-small' src='/images/user/" + ai.sbgurl + "' />" +
            			"<div class='caption'><h5>" + ai.sname + "</h5>" +
            			"<p class='aline'>" + ai.sdesc + "</p></div></div></li>"
            $("#applist-ul").append(elem)
        }
    }
    $("#title").html(appname + " 场景列表")
}

function to(sid) {
    var url = "/page/forward?page=userSceneDetail&sid="+sid
    window.location.href = url
}