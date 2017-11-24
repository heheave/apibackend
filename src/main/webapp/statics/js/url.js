function default_get(req_url, d) {
    if (!d) d = "{}"
	var result = null;
	var option = {
		url : req_url,
		method : 'post',
		data : d,
		cache : false,
		async : false,
		timeout : 5000,
		success : function(res, textStatus) {
			try {
				result = JSON.parse(res);
			} catch (e) {
				result = res;
			}
			return result;
		},
		error : function(res, textStatus) {
			alert(textStatus);
		},
	};
	$.ajax(option);
	return result;
}

function formSubmit(url, d) {
    var form = $('<form />', {action : url, method:"post", style:"display:none;"}).appendTo('body');
    $.each(d, function(k, v) {
        if ( k != "url" ){
            form.append('<input type="hidden" name="' + k +'" value="' + v +'" />');
        }
    });
    // used to prevent csrf attack
    // form.append('<input type="hidden" name="csrfToken" value="' + $("#csrf_token").val() + '" />' );
    form.submit();
}