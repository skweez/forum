goog.provide('net.skweez.forum.DiscussionsService');

/**
 * The Discussions resource as a service. Can be injected as "Discussions".
 */
function DiscussionService() {
	return [ '$resource', function($resource) {
		return $resource('/api/discussions/:discussionId', {
			discussionId : '@id'
		});
	} ];
}