var isAdding = false
var addingId = null
var dcodeList = null
var curPage;
$(document).ready(function() {
    document.getElementById("appdetail-pane").onmousemove = function(e) {
        if (isAdding && e.target == this) {
            var e = e || window.event;
            //console.log("xxxxxxxxxxxxxxxxx" + e.offsetX + ":" + e.offsetX)
            clickOn(e.offsetX, e.offsetY);
        }
    }
    //alert(sid)
    initScene()
});

function clickOn(x, y) {
//    if (!isAdding) return
//    var x = event.clientX;
//    var y = event.clientY;
//    isDown = true;
//    var cont = document.getElementById("appdetail-pane");
//    var div_x = cont.offsetLeft;
//    var div_y = cont.offsetTop;
//    var xOffset = x - div_x
//    var yOffset = y - div_y
//    console.log("鼠标按下了，当前鼠标位置：x="+x+",y="+y+"DIV的位置：x="+div_x+",y="+div_y + "</br>"
//        +"偏移xO = " + xOffset + ", yO = " + yOffset);
    if (addingId) {
        $("#" + addingId).css("left", x - 15)
        $("#" + addingId).css("top", y - 15)
    }

}

function getDot(id, name) {
    var elem = "<label class = 'dot' id = '" + id + "' title = '" + name + "'></label>"
    return elem
}

function setId(id) {
    alert(id)
}

function skipToPage(page) {
    curPage = page
    getDcodesList(page - 1)
}

function getDcodesList(p) {
    dcodeList =  default_get("/pointer/list", {"page": p})
    $("#devListTbl").empty()
    if (!dcodeList) return
    for (var i = 0 ; i < dcodeList.length; i++) {
        var dc = dcodeList[i]
        console.log(dc)
        var isOn = dc.psid && dc.appname
        var elem = "<tr><td>" + dc.pname + "</td><td>" + dc.pdcode +
        "</td><td>" + dc.pcalculate + "</td><td>" + dc.pouttype + "</td>";
        if (isOn) {
            elem += "<td><span class='badge'>已绑</span>"
        } else {
            elem += "<td><span class='badge  badge-success' onclick='adding(" + i + ")'>添加</span>"
        }
        $("#devListTbl").append(elem)
    }
}


function initScene() {
    var ps = default_get("/app/scene", {"sid" : sid})
    console.log(ps)
    if (ps) {
        if (ps.scene.sbgurl) {
            $("#scene-title").html(ps.scene.sname + "场景配置")
            $("#appdetail-pane").attr("style","background:url('/images/user/" + ps.scene.sbgurl + "') no-repeat 50% 50%;");
        }
        for (var i = 0; i < ps.pointer.length; i++) {
            var pid = (ps.pointer[i]).pid
            var pname = (ps.pointer[i]).pname
            var pix = ((ps.dots)[pid]).x
            var piy = ((ps.dots)[pid]).y
            var elem = getDot("pointer" + pid, pname)
            console.log(elem)
            console.log(pix + "-:-" + piy)
            $("#appdetail-pane").append(elem)
            console.log(document.getElementById("appdetail-pane"))
            $("#pointer" + pid).css("left", pix - 15)
            $("#pointer" + pid).css("top", piy - 15)
            ttt("pointer" + pid)
        }
    }

}

function addPointer() {
    console.log("AAAAAA")
}

function adding(i) {
    if (dcodeList && dcodeList[i]) {
        var id = "pointer" + dcodeList[i].pid
        var name = dcodeList[i].pname
        //alert(name)
        isAdding = true
        $("#" + id).remove()
        $("#appdetail-pane").append(getDot(id, name))
        ttt(id)
        addingId= id
    }
}

function ttt(name) {
        document.getElementById(name).onclick = function(e) {
            if (isAdding) {
                if (confirm("确认添加?")) {
                    var cont = document.getElementById(this.id);
                    var div_x = cont.offsetLeft + 15;
                    var div_y = cont.offsetTop + 15;
                    var pid = this.id.replace("pointer", "")
                    var d = {"pid": pid, "sid": sid, "x": div_x, "y":div_y}
                    var response = default_get("/app/pm", d)
                    if (response) {
                        isAdding = false
                        addingId = null
                        alert("添加成功")
                    } else {
                        alert("发生错误")
                        $(this).remove()
                    }
                }
                document.location.reload();
            } else {
                isAdding = true
                addingId = name
            }
        }
}