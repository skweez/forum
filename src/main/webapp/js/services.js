'use strict';

/* Services */

var services = angular.module('net.skweez.forum.services', [ 'ngResource' ]);

/*
 * The Discussions resource as a service. Can be injected as "Discussions".
 */
services.factory('Discussions', [ '$resource', function($resource) {
	return $resource('/api/discussions/:discussionId', {
		discussionId : '@id'
	});
} ]);

/*
 * The Posts resource as a service.
 */
services.factory('Posts', [ '$resource', function($resource) {
	return $resource('/api/discussions/:discussionId/posts/:postId', {
		postId : '@id'
	});
} ]);

/*
 * The AlertService. Can be injected to show alerts.
 * 
 * Usage: pass an object to addAlert with the following attributes:
 * 	type: the bootstrap alert type. Available values: info, success, warning, danger
 * 	title: the title of the alert (will be displayed in bold)
 * 	content: the content of the alert
 */
services.service('AlertService', [function() {
	// store a reference to this for the window.setTimeout function
	var self = this;
	self.alerts = [];
	
	self.addAlert = function(alert) {
		alert.closeAfter = 3000;
		self.alerts.unshift(alert);
		window.setTimeout(function() {
			self.alerts.pop();
		}, 3000);
	};
}]);

/*
 * UserService. Holds the current logged in user infos
 */
services.factory('UserService', [function() {
	return {
		uid : null,
		isLoggedIn : false
	};
}]);