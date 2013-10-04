function PostsViewModel(id) {
	var self = this;
	self.discussionURI = '/api/discussions/' + id;
	self.postsURI = self.discussionURI + '/posts';
	self.discussionId = ko.observable();
	self.discussionTitle = ko.observable();
	self.posts = ko.observableArray();
	self.discussionId(id);

	self.addPost = function(post) {
		nsfAjax(self.postsURI, 'POST', post).done(
				function(data, textStatus, jqXHR) {
					$.getJSON(jqXHR.getResponseHeader("Location"), function(
							post) {
						self.posts.push({
							content : ko.observable(post.content),
							date : ko.observable(post.date)
						});
					});
				});
	}

	// Get discussion from server
	nsfAjax(self.discussionURI, 'GET').done(function(discussion) {
		if (discussion) {
			self.discussionTitle(discussion.title);
		}
	});

	// Get posts for discussion from server
	nsfAjax(self.postsURI, 'GET').done(function(posts) {
		if (posts) {
			for (var i = 0; i < posts.length; i++) {
				self.posts.push({
					content : ko.observable(posts[i].content),
					date : ko.observable(posts[i].date)
				});
			}
		}
	});
}

function CreatePostViewModel() {
	var self = this;
	self.content = ko.observable();

	self.createPost = function() {
		postsViewModel.addPost({
			'Post' : {
				content : self.content().replace(/\n/g, '<br/>'),
				date : new Date()
			}
		});
		self.content("");
	}
}