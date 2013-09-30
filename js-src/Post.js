function Post(content) {
 
    this.id = -1;
 
    this.content = content;
}
 
/** The URL path used to call the server. */
Post.API_PATH = "api/discussions";
 
Post.reloadAndRenderAllPosts = function(domElement) {
    domElement.empty();
    $.getJSON(Post.API_PATH, function(result) {
        $.each(result, function(i, object) {
            Post.fromObject(object).render(domElement);
        });
    });
}
 
Post.fromObject = function(object) {
    var Post = new Post(object.title);
    Post.id = object.id;
    return Post;
}
 
Post.prototype.render = function(domElement) {
    domElement.append('<li>' + this.content + '</li>');
}
 
Post.prototype.toJson = function() {
    return JSON.stringify({
        "Post" : this
    });
}
 
Post.prototype.postPostAndRender = function(domElement) {
    this.postToServer(function(data, textStatus, jqXHR) {
        $.getJSON(jqXHR.getResponseHeader("Location"), function(result) {
            Post.fromObject(result).render(domElement);
        });
    });
}
 
Post.prototype.postToServer = function(successFunction) {
    $.ajax({
    		type: "POST",
    		url: Post.API_PATH,
    		contentType: "application/json",
    		data: this.toJson(),
    		success: successFunction
    });
}