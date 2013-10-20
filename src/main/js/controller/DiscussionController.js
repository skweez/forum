goog.provide('net.skweez.forum.controller.DiscussionController');

goog.require('net.skweez.forum.services');

/**
 * The DiscussionController. Used to control single discussions.
 */
function DiscussionController() {
	return [ '$scope', '$routeParams', 'Discussions', 'Posts',
			function($scope, $routeParams, Discussions, Posts) {
				$scope.newPost = {};

				// Get current discussion
				$scope.discussion = Discussions.get({
					discussionId : $routeParams.discussionId
				});

				// Get all the posts for this discussion
				$scope.posts = Posts.query({
					discussionId : $routeParams.discussionId
				});

				// Create a new post
				$scope.createPost = function() {
					Posts.save({
						discussionId : $routeParams.discussionId
					}, {
						'Post' : $scope.newPost
					}, function() {
						// save success function: add new post to displayed
						// posts
						var post = angular.copy($scope.newPost);
						// TODO (elm): Can we assume now() is close enough to
						// the
						// date the servers sets?
						post.date = new Date();
						$scope.posts.push(post);
						// empty the new post box
						$scope.newPost.content = "";
					});
				};
			} ];
}