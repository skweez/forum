goog.provide('net.skweez.forum.AlertController');

goog.require('net.skweez.forum.services');

/**
 * Alert controller. Used to display alerts.
 */
function AlertController() {
	return [ '$scope', 'AlertService', function($scope, AlertService) {
		$scope.alertService = AlertService;
	} ];
}
