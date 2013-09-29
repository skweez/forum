function Discussion(title) {
 
    this.id = -1;
 
    this.title = title;
}
 
/** The URL path used to call the server. */
Discussion.API_PATH = "api/discussions";
 
Discussion.reloadAndRenderAllDiscussions = function(domElement) {
    domElement.empty();
    $.getJSON(Discussion.API_PATH, function(result) {
        $.each(result, function(i, object) {
            Discussion.fromObject(object).render(domElement);
        });
    });
}
 
Discussion.fromObject = function(object) {
    var discussion = new Discussion(object.title);
    discussion.id = object.id;
    return discussion;
}
 
Discussion.prototype.render = function(domElement) {
    domElement.append('<li><a href="discussion.html?id=' + this.id + '">'
            + this.title + '</a></li>');
}
 
Discussion.prototype.toJson = function() {
    return JSON.stringify({
        "Discussion" : this
    });
}
 
Discussion.prototype.postDiscussionAndRender = function(domElement) {
    this.postToServer(function(data, textStatus, jqXHR) {
        $.getJSON(jqXHR.getResponseHeader("Location"), function(result) {
            Discussion.fromObject(result).render(domElement);
        });
    });
}
 
Discussion.prototype.postToServer = function(successFunction) {
    $.ajax({
    		type: "POST",
    		url: Discussion.API_PATH,
    		contentType: "application/json",
    		data: this.toJson(),
    		success: successFunction
    });
}