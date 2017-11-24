function notNullElse(v, f, a) {
	if (v) {
	    return a ? v + a : v
	} else {
	    return f ? f : "-"
	}
}

function floatFixed(f, n) {
    if (!f) return "-"
    if (!n) n = 2
    return f.toFixed(n)
}

function bFormat(b) {
    console.log(b)
    if (!b || b < 0) return "-"
    if (b < 8) return b + "b"
    if (b >= 8 && b < 8000) return (b / 8).toFixed(2) + "B"
    if (b >= 8000 && b < 8000000) return (b / 8000).toFixed(2) + "KB"
    if (b >= 8000000 && b < 8000000000) return (b / 8000000).toFixed(2) + "MB"
    if (b >= 8000000000 && b < 8000000000000) return (b / 8000000000).toFixed(2) + "GB"
    else return "Out Range"
}

function dateFormatByPattern(date, pattern) {
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    return date.format(pattern);
}

function dateFormat(timestamp, isFull) {
        if (timestamp) {
        var pattern = "";
        if (isFull == true || isFull == undefined) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        } else {
            pattern = "yyyy-MM-dd";
        }
        return dateFormatByPattern(new Date(timestamp), pattern);
    } else {
        return "-"
    }
}

function aryToString(ary, c) {
    var res = ""
    if (!c) c = " , "
    for (var i = 0; i < ary.length; i++) {
        if (i == 0) {
            res += ary[i]
        } else {
            res += c + ary[i]
        }
    }
    return res
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function isEmptyObject(e) {
    if (e) {
        var t;
        for (t in e)
            return !1;
        return !0
    } else {
        return true
    }
}