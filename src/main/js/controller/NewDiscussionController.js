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
			'discussions',
			function($scope, $http, $rootScope, discussions) {
				$scope.title = null;
				$scope.content = null;

				$scope.createDiscussion = function() {
					var newDiscussion = new Object({
						title : $scope.title,
						posts : [ {
							content : $scope.content
						} ]
					});
					discussions.save(newDiscussion, function(data,
							getResponseHeaders) {
						$('#newDiscussion').modal('hide');
						$rootScope.$broadcast('discussionAdded', {
							'location' : getResponseHeaders('location')
						});
					});
				};
			} ];
}