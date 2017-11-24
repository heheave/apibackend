$(document).ready(function() {
    $('#toggle-a').click(function() {
        var userList = default_get("/user/getUsersJson")
        var appList = default_get("/app/getAppsJson")
        console.log(userList)
        console.log(appList)
        $("#seluid").empty()
        if (userList) {
            for (var i = 0; i < userList.length; i++) {
                var item = "<option value='" + userList[i].uid + "'>" + userList[i].unick + "</option>"
                $("#seluid").append(item)
            }
        }

        $("#selaid").empty()
        if (appList) {
            for (var i = 0; i < appList.length; i++) {
                var item = "<option value='" + appList[i].appId + "'>" + appList[i].appName + "</option>"
                $("#selaid").append(item)
            }
        }
    });
});

function addSubmit() {
    var uname = $('#seluid option:selected').text();
    var aname = $('#selaid option:selected').text();
    if (confirm("将应用" + aname + "分配给" + uname + "?")) {
        var uid = $('#seluid option:selected').val();
        var aid = $('#selaid option:selected').val();
        //alert(uid + ":" + aid)
        var ret = default_get("/user/appAdd", {"appId":aid, "uid":uid})
        if (ret) {
            alert("分配成功!")
        } else {
            alert("分配失败!")
        }
    }
    //window.location.href = "/user/getUsers"
}

function addCancel() {
    window.location.href = "/user/getUsers"
}

function showDetail(uid) {
    var url = "/user/getUser"
    var body = {"uid" : uid}
    var userinfo = default_get(url, body)
    if (userinfo) {
        console.log(userinfo)
         $("#user-nick").html(userinfo.unick)
         $("#detail-uid").html("<b>用户Id:&ensp;&ensp;</b>" + userinfo.uid)
         $("#detail-uname").html("<b>姓名:&ensp;&ensp;</b>" + userinfo.uname)
         $("#detail-uphone").html("<b>电话:&ensp;&ensp;</b>" + notNullElse(userinfo.uphone))
         $("#detail-ucom").html("<b>公司:&ensp;&ensp;</b>" + notNullElse(userinfo.ucom))
         $("#detail-uemail").html("<b>Email:&ensp;&ensp;</b>" + notNullElse(userinfo.uemail))
         $("#detail-ulevel").html("<b>用户级别:&ensp;&ensp;</b>" + notNullElse(userinfo.ulevel))
         $("#detail-udesc").html("<b>用户描述:&ensp;&ensp;</b>" + notNullElse(userinfo.udesc))
         $("#detail-utime").html("<b>创建时间:&ensp;&ensp;</b>" + notNullElse(userinfo.utime))
         $("#detail-appNum").html("<b>拥有App名称:&ensp;&ensp;</b>" + userinfo.allAppName)
    }
      $("#tenantList").hide();
      $("#tenantDetail").show();
}

function back() {
      $("#tenantDetail").hide();
      $("#tenantList").show();
}