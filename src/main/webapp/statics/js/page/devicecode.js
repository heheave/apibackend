$(document).ready(function() {
    $('#toggle-a').click(function() {
        var userList = default_get("/user/getUsersJson", {})
        $("select#select01").empty()
        if (userList) {
            for (var i = 0; i < userList.length; i++) {
                var item = "<option value='" + userList[i].uid + "'>" + userList[i].unick + "</option>"
                $("select#select01").append(item)
            }
        }
    });
});

function addSubmit() {
    var num = $("input#input01").val()
    var nick = $("select#select01 option:selected").val()
    if (num > 0 && nick) {
        //alert(num + ":" + nick)
        $("#code-gen-form").submit();
    } else {
        alert("请正确填写数量和选择租户")
    }
}

function addCancel() {
    window.location.href="/page/forward?page=devicecode"
}