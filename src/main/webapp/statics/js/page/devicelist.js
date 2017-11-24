var dcodeList = null
$(document).ready(function() {

});

function getDcodesList(p) {
    dcodeList =  default_get("/device/list-dcode", {"page": p})
    //console.log(dcodeList)
    $("#devListTbl").empty()
    if (!dcodeList) return
    for (var i = 0 ; i < dcodeList.length; i++) {
        var dc = dcodeList[i]
        var isOn = dc.detail && dc.detail.machineIp
        var elem = "<tr><td>" + dc.dcode.dccode + "</td><td>" + notNullElse(dc.dcode.dcnick) +
        "</td><td>" + notNullElse(dc.detail.protocolType) + "</td><td>" + notNullElse(dc.detail.machineIp) + "</td>";
        if (isOn) {
            elem += "<td><span class='badge badge-success' onclick = 'devDetail(" + i + ")' style = 'cursor:pointer'>在线</span>"
        } else {
            elem += "<td><span class='badge'>离线</span>"
        }
        elem += "</td></tr>"
        $("#devListTbl").append(elem)
    }
}

function devDetail(e) {
    var detail = dcodeList[e]
    $("#devList").hide();
    $("#devDetail").show();
    if (detail) {
        $("#dcode").html(notNullElse(detail.dcode.dccode))
        $("#dcnick").html(notNullElse(detail.dcode.dcnick))
        $("#pro").html(notNullElse(detail.detail.protocolType))
        $("#mip").html(notNullElse(detail.detail.machineIp))
        $("#ftime").html(dateFormat(detail.detail.firstTime))
        $("#ltime").html(dateFormat(detail.detail.lastTime))
        $("#pavg").html(notNullElse(detail.detail.interval, "-", " ms"))
        $("#dmn").html(notNullElse(detail.detail.msgNum))
        $("#dmb").html(notNullElse(detail.detail.msgByte ? detail.detail.msgByte / 1000000 : 0, "-", " Mb"))
        $("#dma").html(notNullElse(detail.detail.msgAttr))
    }
}

function back() {
      $("#devDetail").hide();
      $("#devList").show();
}

function skipToPage(page) {
    //console.log("xxxxxxxxxxxxxxxx")
    getDcodesList(page - 1)
}