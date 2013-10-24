goog.provide('net.skweez.forum.controller.DiscussionOverviewController');

goog.require('net.skweez.forum.services');

/**
 * Displays a list of discussions.
 */
function DiscussionOverviewController() {
	return [
			'$scope',
			'$http',
			'$resource',
			'discussions',
			'alertService',
			'userService',
			function($scope, $http, $resource, discussions, alertService,
					userService) {
				$scope.discussions = discussions.query();

				$scope.showNewDiscussionDialog = function() {
					if (!userService.isLoggedIn) {
						alertService
								.addAlert({
									type : "warning",
									title : "",
									content : "You need to be logged in to create a new Discussion."
								});
					} else {
						$('#newDiscussion').modal('show');
					}
				};

				// Listen for 'discussionAdded' broadcast and update
				// view
				$scope.$on('discussionAdded', function(event, args) {
					$http.get(args.location).success(function(discussion) {
						$scope.discussions.push(discussion);
					});
				});
			} ];
}