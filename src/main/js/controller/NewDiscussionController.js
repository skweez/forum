goog.provide('net.skweez.forum.controller.NewDiscussionController');

goog.require('net.skweez.forum.services');

/**
 * The NewDiscussionController. Used to create a new discussion with on initial
 * post.
 */
function NewDiscussionController() {
	return [
			'$scope',
			'$http',
			'$rootScope',
			'Discussions',
			function($scope, $http, $rootScope, Discussions) {
				$scope.title = null;
				$scope.content = null;

				$scope.createDiscussion = function() {
					var newDiscussion = new Object({
						'Discussion' : {
							title : $scope.title,
							posts : {
								'Post' : {
									content : $scope.content
								}
							}
						}
					});
					Discussions.save(newDiscussion, function(data,
							getResponseHeaders) {
						$('#newDiscussion').modal('hide');
						$rootScope.$broadcast('discussionAdded', {
							'location' : getResponseHeaders('location')
						});
					});
				};
			} ];
}