var meta = null;

$(document).ready(function() {
    updatePageOnce()
});

function updatePageOnce() {
    getData()
    initMata()
}

function getData() {
    var url = "/config/getEntry"
    var d = {"caname": appName, "cdmark": dmark, "cpidx": port}
    meta = default_get(url, d)
    console.log(meta)
}

function initMata() {
    $("#cmd").val(notNullElse1(meta.ccmd, "input conf cmd"))
    $("#avg").val(notNullElse1(meta.cavg, "input conf avg"))
    //$("#dcompany").html(notNullElse(meta.dcompany))
    //$("#dlocation").html(notNullElse(meta.dlocX) + " , " + notNullElse(meta.dlocY))
    //$("#ddes").html(notNullElse(meta.ddes))
}

function back() {
    history.go(-1)
}

function del() {
    if (confirm("Delete Conf Entry?")) {
        var url = "/config/removeEntry"
        console.log(meta.cid)
        if (meta.cid) {
            var d = {"cid": meta.cid, "caname": appName, "cdmark": dmark}
            formSubmit(url, d)
        } else {
            back()
        }
    }
}

function modify() {
    if (confirm("Modify Conf Entry?")) {
        var cmd = trim(document.getElementById("cmd").value)
        var d = {"caname": appName, "cdmark": dmark}
        if (!cmd || cmd == "input conf cmd") {
            cmd = null
        }
        if (cmd) d["ccmd"] = cmd
        var avg = trim(document.getElementById("avg").value)
        if (!avg || avg == "input conf avg") {
            avg = null
        }
        if (avg) d["cavg"] = avg
        console.log(d)
        var url;
        if (meta.cid) {
            url = "/config/updateEntry"
            d["cid"] = meta.cid
        } else {
            url = "/config/addEntry"
            d["cpidx"] = port
        }
        formSubmit(url, d)
    }
}