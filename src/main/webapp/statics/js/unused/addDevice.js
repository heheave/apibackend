$(document).ready(function() {
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

function back() {
    history.go(-1)
}

function mysubmit() {
        document.getElementById("appName").value = appName
        console.log(appName)
        var did = trim(document.getElementById("did").value)
        document.getElementById("did").value = did
        var type = trim(document.getElementById("type").value)
        if (!type || type == "input device type") {
            type = null
        }
        document.getElementById("type").value = type

        var company = trim(document.getElementById("company").value)
        if (!company || company == "input company name") {
            company = null
        }
        document.getElementById("company").value = company

        var longitude = trim(document.getElementById("longitude").value)
        if (!longitude || longitude == "input loc longitude") {
            longitude = null
        }
        document.getElementById("longitude").value = longitude

        var latitude = trim(document.getElementById("latitude").value)
        if (!latitude || latitude == "input loc latitude") {
            latitude = null
        }
        document.getElementById("latitude").value = latitude

        var desc = trim(document.getElementById("desc").value)
        if (!desc || desc == "input device desc") {
            desc = null
        }
        document.getElementById("desc").value = desc

        var portnum = trim(document.getElementById("portnum").value)
        if (!portnum || portnum == "set port number") {
            portnum = null
        }
        document.getElementById("portnum").value = portnum
        $("#addForm").submit()
}

function popunshow(portnum) {
	$("#gray").show();
	$("#popup").show();
	$("#portcell").empty()
	for (var i = 0; i < portnum; i++) {
        var item = "<div class='input_div'>" +
        		   "<div class='myinput_cell'>" +
        		   "Port" + i + ": <input name = 'dports[" + i + "].ddes' id='port" + i + "'/>" +
        		   "</div><div class='myinput_cell'>" +
                   "Unit: <input name = 'dports[" + i + "].dunit' id='unit" + i + "'/>" +
                    "</div></div>";
	    $("#portcell").append(item)
	}
	var _top = ($(window).height() - $(".popup").height()) / 2;
    var _left = ($(window).width() - $(".popup").width()) / 2;
    console.log($(window).width())
    console.log($("#popup").width())
    console.log(_left)
    if (_top < 0) _top = 0
    $("#popup").css({
        top : _top,
        left : _left
    });
	//document.getElementById("bianhao").focus();
}

function popunhide() {
	$("#gray").hide();
	$("#popup").hide();
	//document.getElementById("back").focus();
}

function next() {
    var did = trim(document.getElementById("did").value)
    if (did != "" && did != "input device id") {
        var portnum = parseInt(trim(document.getElementById("portnum").value), 10)
        if (portnum) {
            popunshow(portnum)
        } else {
            alert("Illegal port number")
        }
    } else {
        alert("Did should be specified")
    }

}
