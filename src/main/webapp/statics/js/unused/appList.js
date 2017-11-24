var meta = null;

$(document).ready(function() {
    updatePageOnce()
});

function updatePageOnce() {
    getData()
    initAppMata()
}

function getData() {
    var meta_url = "/app/list"
    meta = default_get(meta_url)
    console.log(meta)
}

function initAppMata() {
    $("tr").remove(".scss_value_tr")
    for (var i = 0; i < meta.length; i++) {
        var appMeta = meta[i]
        var idx = i + 1
        var name = notNullElse(appMeta.appName)
        var company = notNullElse(appMeta.acompany)
        var desc = notNullElse(appMeta.adesc)
        var crttime = notNullElse(appMeta.crtTime)
        var item = "<tr class = 'scss_value_tr'><td class = 'scss_td'> "
        + idx + "</td><td class = 'scss_td'><a href = '/page/app?appId=" + appMeta.appId + "'>"
        + appMeta.appName + "</a></td><td class = 'scss_td'> " + company  + "</td>"
        + "<td class = 'scss_td'> " + desc  + "</td><td class = 'scss_td'> " + crttime  + "</td></tr>"
        $("#app-table").append(item)
    }
}

function update() {
    updatePageOnce()
    alert("刷新成功")
}

function create() {
    window.location = "/page/forward?page=addApp"
}