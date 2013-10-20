goog.provide('net.skweez.forum.service.UserService');

/**
 * UserService. Holds the current logged in users informations.
 */
function UserService() {
	return [ function() {
		return {
			uid : null,
			isLoggedIn : false
		};
	} ];
}
