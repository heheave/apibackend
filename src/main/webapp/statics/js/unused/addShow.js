var devices = null
var compareNum = null

$(document).ready(function() {
    getDevices()
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

	$("#addForm").keypress(function(e) {
      if (e.which == 13) {
        return false;
      }
    });

});

function getDevices() {
    var url = "/app-device/getAppDeviceByName"
    var d = {"appName": appName}
    devices = default_get(url, d)
    console.log(devices)
    document.getElementById("type").onchange = function(){
        var type = $("#" + this.id + " option:selected").val()
        if (type != "CMP") {
            var cnum = document.getElementById("cnum")
            cnum.value = 2
            cnum.disabled = true
        } else {
            var cnum = document.getElementById("cnum")
            cnum.value = "input comparison number"
            cnum.disabled = false
        }
    }

}

function back() {
    history.go(-1)
}

function mysubmit() {
        var submitData = {}
        submitData["appName"] = appName
        var type = $("#type option:selected").val()
        submitData["stype"] = type
        var desc = trim(document.getElementById("desc").value)
        if (desc) submitData["sdesc"] = desc
        var cnum = parseInt(trim(document.getElementById("cnum").value))
        if (cnum) submitData["slen"] = cnum
        var map = {}
        var dids = []
        var ports = []
        for (var i = 0; i < compareNum; i++) {
            var did = $("#dev" + i + " option:selected").val()
            dids.push(did)
            var port = $("#desc" + i + " option:selected").val()
            ports.push(port)
            if (map[did + "-" + port]) {
                alert("Contains Same Device And Port")
                return
            } else {
                map[did + "-" + port] = 1
            }
        }
        function checkOnlyADevice(ary) {
            if (ary.length < 1) {
                return false
            } else {
                var firstElem = ary[0]
                for (var i = 1; i < ary.length; i++) {
                    if (firstElem != ary[i]) return false
                }
                return true
            }
        }
        if (type != "CMP" && !checkOnlyADevice(dids)) {
            alert("Only support " + type + " on a device")
            return
        }
        var didsStr = aryToString(dids, ":")
        var portsStr = aryToString(ports, ":")
        submitData["sdids"] = didsStr
        submitData["sports"] = portsStr
        formSubmit("/app-show/addAppShow", submitData)
}

function popunshow(portnum) {
	$("#gray").show();
	$("#popup").show();
	$("#portcell").empty()
	for (var i = 0; i < portnum; i++) {
        var item = "<div class='input_div'>" +
        		   "<div class='myinput_cell'>" +
        		   "Device " + i + ": " + getDevicesSelect("dev" + i) +
        		   "</div><div class='myinput_cell'>" +
                   "Port Desc: <select id='desc" + i + "'></select>" +
                    "</div></div>";
	    $("#portcell").append(item)
	}
	var _top = ($(window).height() - $(".popup").height()) / 2;
    var _left = ($(window).width() - $(".popup").width()) / 2;
    //console.log($(window).width())
    //console.log($("#popup").width())
    //console.log(_left)
    if (_top < 0) _top = 0
    $("#popup").css({
        top : _top,
        left : _left
    });
    for (var i = 0; i < portnum; i++) {
        var selId = "dev" + i
        var selector = document.getElementById(selId)
        getPortSelect(i, selector.selectedIndex)
        selector.onchange = function(){
            var didx = document.getElementById(this.id).selectedIndex
            var id = this.id.replace(/[^0-9]/ig, "")
            getPortSelect(id, didx)
        }
    }
    compareNum = portnum
}

function popunhide() {
	$("#gray").hide();
	$("#popup").hide();
	//document.getElementById("back").focus();
}

function next() {
    var desc = trim(document.getElementById("desc").value)
    if (desc != "" && desc != "input device type") {
        var dnum = parseInt(trim(document.getElementById("cnum").value), 10)
        if (dnum) {
            popunshow(dnum)
        } else {
            alert("Illegal device number")
        }
    } else {
        alert("Desc should be specified")
    }
}

function getDevicesSelect(selId) {
    var selector = "<select id = '" + selId + "'>"
    for (var i = 0; i < devices.length; i++) {
        var did = devices[i].did
        var dmark = devices[i].dmark
        var opt = "<option value ='" + did + "'>" + dmark + "</option>"
        selector += opt
    }
    selector += "</select>"
    return selector
}

function getPortSelect(selId, didx) {
    $("#desc" + selId).empty()
    var len = devices[didx].dports.length
    for (var i = 0; i < len; i++) {
        var port = (devices[didx].dports)[i].dport
        var desc = (devices[didx].dports)[i].ddes
        var opt = "<option value ='" + port + "'>" + desc + "</option>"
        $("#desc" + selId).append(opt)
    }
}
