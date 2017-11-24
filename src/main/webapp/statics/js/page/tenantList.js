$(document).ready(function() {

});

function addCancel() {
    history.go(-1)
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
         $("#detail-udevicecode").html("<b>申请设备码数:&ensp;&ensp;</b>" + userinfo.dcNum)
         $("#detail-appnum").html("<b>创建App名称:&ensp;&ensp;</b>" + userinfo.allAppName)
                                 
        
    }
      $("#tenantList").hide();
      $("#tenantDetail").show();
}

function back() {
      $("#tenantDetail").hide();
      $("#tenantList").show();
}