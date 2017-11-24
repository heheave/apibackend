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
        var elem = "<tr><td>" + dc.dcode.dccode + "</td><td>"
        + notNullElse(dc.detail.protocolType) + "</td><td>"
        + notNullElse(dateFormat(dc.detail.firstTime)) + "</td><td>";
        if (isOn) {
            elem += "<span class='badge badge-success' onclick = 'devConf(" + i + ")' style = 'cursor:pointer'>配置</span>"
        } else {
            elem += "<a class='badge' data-toggle='tooltip' title='需要先确定设备接入平台' onclick = 'devDetail(" + i + ")' style = 'background:transparent; cursor:pointer'><img src='/images/question.ico' class='img-circle'></a>"
        }
        elem += "</td></tr>"
        $("#devListTbl").append(elem)
    }
}

function devConf(e) {
    var detail = dcodeList[e]
    if (detail) {
        var dcode = detail.dcode.dccode
        var url = "/page/forward?page=pointerAdd&dccode=" + dcode
        window.location.href = url
    } else {
        $("#devList").hide();
        $("#devDetail").show();
    }
    //if (detail) {
    //    $("#dcode").html(notNullElse(detail.dcode.dccode))
    //    $("#dcnick").html(notNullElse(detail.dcode.dcnick))
    //    $("#pro").html(notNullElse(detail.detail.protocolType))
    //    $("#mip").html(notNullElse(detail.detail.machineIp))
    //    $("#ftime").html(dateFormat(detail.detail.firstTime))
    //    $("#ltime").html(dateFormat(detail.detail.lastTime))
    //    $("#pavg").html(notNullElse(detail.detail.interval, "-", " ms"))
    //    $("#dmn").html(notNullElse(detail.detail.msgNum))
    //    $("#dmb").html(notNullElse(detail.detail.msgByte ? detail.detail.msgByte / 1000000 : 0, "-", " Mb"))
    //    $("#dma").html(notNullElse(detail.detail.msgAttr))
    //}
}

function back() {
      $("#devDetail").hide();
      $("#devList").show();
}

function skipToPage(page) {
    //console.log("xxxxxxxxxxxxxxxx")
    getDcodesList(page - 1)
}