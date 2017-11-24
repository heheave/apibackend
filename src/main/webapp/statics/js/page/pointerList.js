var dc = null

$(document).ready(function() {

});

function getAttr(dccode) {
    $("#dccode-title").html("设备码: " + dccode)
    dc = default_get("/device/get-dcode", {"dccode" : dccode})
    console.log(dc)
    $("tbody").empty()
    if (dc && dc.detail) {
        for (key in dc.detail.msgAttr) {
            var elem = "<tr><td><a>" + key + "</a></td><td>" + (dc.detail.msgAttr)[key]+ "</td></tr>"
            //attrMap[key] = (dc.detail)[key]
            $("tbody").append(elem)
        }
    }
    $("td a").click(function() {
        var attr = this.text
        var oldStr = $("#category").val()
        console.log(attr)
        console.log(oldStr)
        var newStr = oldStr + attr
        $("#category").val(newStr)
    });
}

function addSubmit() {
    var cal = $("#category").val()
    console.log(cal)
    if (!dc) return
    var attrMap = {}
    var attrs = cal.split(/[+,\-,\*,/,(,)]/)
    if (attrs) {
        var legal = true;
        for (var i = 0; i < attrs.length; i++) {
            if (!attrs[i]) {
                continue
            }
            if (!((dc.detail.msgAttr)[attrs[i]])) {
                legal = false
                break
            }
            attrMap[attrs[i]] = (dc.detail.msgAttr)[attrs[i]]
        }
        if (!legal) {
            alert("计算规则错误")
            return
        } else {
            console.log(attrMap)
        }
        console.log(attrMap)
        var str = ""
        for (key in attrMap) {
            if (attrMap[key] != "NUMBER") {
                alert("now only support number calculate")
                return
            }
            str += (key + ":")
        }
        if (confirm("绑定量为 " + str + "?")) {
            var pdcode = "<input name='pdcode' value='" + dcode + "' style='display:none'/>"
            var pbindattr = "<input name='pbindattr' value='" + str + "' style='display:none'/>"
            $("#addPointerForm").append(pdcode)
            $("#addPointerForm").append(pbindattr)
            $("#addPointerForm").submit()
        }
    }
}

function addCancel() {
    history.go(-1)
}

function showDetail(uid) {

}

function back() {
    $("#pointerDetail1").hide()
    $("#pointerList").show()
}

function getDcodesList(p) {
    dcodeList =  default_get("/pointer/list", {"page": p})
    //console.log(dcodeList)
    $("#devListTbl").empty()
    if (!dcodeList) return
    for (var i = 0 ; i < dcodeList.length; i++) {
        var dc = dcodeList[i]
        console.log(dc)
        var isOn = dc.psid && dc.appname
        var elem = "<tr><td>" + dc.pname + "</td><td>" + dc.pdcode +
        "</td><td>" + dc.pbindhash + "</td><td>" + dc.pouttype + "</td>";
        if (isOn) {
            elem += "<td><span class='badge badge-success'>已绑</span>"
        } else {
            elem += "<td><span class='badge'>未绑</span>"
        }
        elem += "</td><td><a onclick='detail(" + i + ")' class='view-link'>查看</a).html()"
        $("#devListTbl").append(elem)
    }
}

function detail(e) {
    var dc = dcodeList[e]
    $("#pointerList").hide()
    $("#pointerDetail1").show()
    if (dc) {
        $("#pname").html(dc.pname)
        $("#pdcode").html(dc.pdcode)
        $("#pbindhash").html(dc.pbindhash)
        $("#pcalculate").html(dc.pcalculate)
        $("#poutdesc").html(dc.poutdesc)
        $("#pouttype").html(dc.pouttype)
        $("#pavg").html(dc.pavg)
        $("#poutunit").html(dc.poutunit)
        $("#pdesc").html(dc.pdesc)
        $("#padd").html(dc.appname)
    }
}

function skipToPage(page) {
    //console.log("xxxxxxxxxxxxxxxx")
    getDcodesList(page - 1)
}