var fileName = null
$(document).ready(function() {
    showList()
});

function back() {
    history.go(-1)
}

function showList() {
    var url = "/app/scene-list"
    var appinfo = default_get(url, {"appId": appId})
    if (appinfo) {
        console.log(appinfo)
        $("#applist-ul").empty()
        for(var i = 0; i < appinfo.length; i++) {
            var ai = appinfo[i]
            var elem = "<li class='span3'><div class='thumbnail' onclick = 'to(" + ai.sid + ")'>" +
            			"<img class = 'img-small' src='/images/user/" + ai.sbgurl + "' />" +
            			"<div class='caption'><h5>" + ai.sname + "</h5>" +
            			"<p class='aline'>" + ai.sdesc + "</p></div></div></li>"
            $("#applist-ul").append(elem)
        }
    }
    $("#tenantList").hide();
    $("#tenantDetail").show();
}

function to(sid) {
    var url = "/page/forward?page=scenelist&sid="+sid
    window.location.href = url
}

function addSubmit() {
    $("#said").val(appId)
    var elem = "<input type='text' name = 'sbgurl' value = '" + fileName + "' style = 'display:none'/>"
    $("#addAppForm").append(elem);
    $("#addAppForm").submit();
}

function addCancel() {
    var url = "/page/forward?page=sceneAdd&appId=" + appId
    window.location.href = url
}

function uploadFile(obj) {
	if (checkSize(obj)) {
		var url = "/file/upload";
		var eid = obj.id;
		var d = {};
		var data = uploadFile_api(url, eid, d, function(fdata) {
			fileName = fdata;
		});

	}
}