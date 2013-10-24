goog.provide('net.skweez.forum.service.DiscussionsService');

/**
 * The Discussions resource as a service. Can be injected as "Discussions".
 */
function DiscussionsService() {
	return [ '$resource', function($resource) {
		return $resource('/api/discussions/:discussionId', {
			discussionId : '@id'
		});
	} ];
}