goog.provide('net.skweez.forum.service.PostsService');

/**
 * The Posts resource as a service.
 */
function PostsService() {
	return [ '$resource', function($resource) {
		return $resource('/api/discussions/:discussionId/posts/:postId', {
			postId : '@id'
		});
	} ];
}