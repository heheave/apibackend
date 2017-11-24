function uploadFile_api(urlStr, eid, d,sf) {
	$.ajaxFileUpload({
		url : urlStr,
		secureuri : false,
		fileElementId : eid,
		data : d,
		dataType : 'text/html',
		success : function(data) {
			sf(data);
		},
		error : function(data, status) {
			alert("服务器或网络问题，上传失败");
		}
	});
}

function checkSize(target) {
	var fileSize = 0;
	var isIE = !-[ 1, ];
	if (isIE && !target.files) {
		var filePath = target.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}
	var size = fileSize / 1024 / 1024;
	if (size > 10) {
		alert("附件不能大于10M");
		target.value = "";
		return false;
	}
	return true;
}