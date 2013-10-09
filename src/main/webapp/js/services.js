'use strict';

/* Services */

var factories = angular.module('net.skweez.forum.services', [ 'ngResource' ]);

/*
 * The Discussions resource as a service. Can be injected as "Discussions".
 */
factories.factory('Discussions', [ '$resource', function($resource) {
	return $resource('/api/discussions/:discussionId', {
		discussionId : '@id'
	});
} ]);

/*
 * The Posts resource as a service.
 */
factories.factory('Posts', [ '$resource', function($resource) {
	return $resource('/api/discussions/:discussionId/posts/:postId', {
		postId : '@id'
	});
} ]);
