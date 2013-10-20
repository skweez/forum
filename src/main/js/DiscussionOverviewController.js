goog.provide('net.skweez.forum.DiscussionOverviewController');

goog.require('net.skweez.forum.services');

/**
 * Displays a list of discussions.
 */
function DiscussionOverviewController() {
	return [
			'$scope',
			'$http',
			'$resource',
			'Discussions',
			'AlertService',
			'UserService',
			// TODO (mks) Are these some kind of "magic" names?
			// Would it be possible that they use the standard
			// naming, i.e. scope, http, alertService, etc.?
			function($scope, $http, $resource, Discussions, AlertService,
					UserService) {
				$scope.discussions = Discussions.query();

				$scope.showNewDiscussionDialog = function() {
					if (!UserService.isLoggedIn) {
						AlertService
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