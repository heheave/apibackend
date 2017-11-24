
$(document).ready(function() {

});

function back() {
    history.go(-1)
}

function submit() {
    var appName = trim(document.getElementById("appName").value)
    if (appName != "" || appName != "input app name") {
        document.getElementById("appName").value = appName
        var company = trim(document.getElementById("company").value)
        if (!company || company == "input company name") {
            company = null
        }
        document.getElementById("company").value = company
        var desc = trim(document.getElementById("desc").value)
        if (!desc || desc == "input app desc") {
            desc = null
        }
        document.getElementById("desc").value = desc
        $("#addForm").submit()
    } else {
        alert("AppName should be specified")
    }

}