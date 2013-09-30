goog.provide('skweez.forum.Utils')

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

nsfGetQueryString = function()
{
    var queryString= [];
    var hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        queryString.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return queryString;
}
nsfGetIdFromQueryString = function() {
	var regexPattern = /(?:id=)(\d+)/;
	var match = window.location.href.match(regexPattern);
	if (match == null) {
		return -1;
	}
	return match[1];
}