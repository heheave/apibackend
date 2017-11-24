function textareaOnclick(obj, defultVal) {
    if (obj.value == defultVal)
    obj.value = '';
    $(obj).css("color", "black");
}

function textareaOnblur(obj, defultVal) {
	if (obj.value == '') {
        $(obj).css("color", "gray");
        obj.value = defultVal;
    }
}

function trim(str){
     return str.replace(/(^\s*)|(\s*$)/g, '');
}
