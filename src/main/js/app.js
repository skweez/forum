goog.provide('net.skweez.forum.app');

angular.module(
		'net.skweez.forum',
		[ 'net.skweez.forum.filters', 'net.skweez.forum.services',
				'net.skweez.forum.directives', 'net.skweez.forum.controllers',
				'$strap.directives', 'ngSanitize' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/discussions', {
				templateUrl : 'partials/discussions.html',
				controller : 'DiscussionsOverviewController'
			});
			$routeProvider.when('/discussions/:discussionId', {
				templateUrl : 'partials/discussion.html',
				controller : 'DiscussionController'
			});
			$routeProvider.otherwise({
				redirectTo : '/discussions'
			});
		} ]);
