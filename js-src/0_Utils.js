// net.skweez.forum.Ajax method
nsfAjax = function(uri, method, data) {
	var request = {
		url : uri,
		type : method,
		contentType: 'application/json',
		data : JSON.stringify(data),
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("ajax error: " + jqXHR.status + " " + textStatus + " " + errorThrown);
		}
	};
	return $.ajax(request);
}