goog.provide('net.skweez.forum.controller.DiscussionController');

goog.require('net.skweez.forum.services');

/**
 * The DiscussionController. Used to control single discussions.
 */
function DiscussionController() {
	return [ '$scope', '$routeParams', 'discussions', 'posts',
			function($scope, $routeParams, discussions, posts) {
				$scope.newPost = {};

				// Get current discussion
				$scope.discussion = discussions.get({
					discussionId : $routeParams.discussionId
				});

				// Get all the posts for this discussion
				$scope.posts = posts.query({
					discussionId : $routeParams.discussionId
				});

				// Create a new post
				$scope.createPost = function() {
					posts.save({
						discussionId : $routeParams.discussionId
					}, {
						'Post' : $scope.newPost
					}, function() {
						// save success function: add new post to displayed
						// posts
						var post = angular.copy($scope.newPost);
						post.date = new Date();
						$scope.posts.push(post);
						// empty the new post box
						$scope.newPost.content = "";
					});
				};
			} ];
}