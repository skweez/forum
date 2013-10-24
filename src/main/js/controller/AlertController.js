goog.provide('net.skweez.forum.controller.AlertController');

goog.require('net.skweez.forum.services');

/**
 * Alert controller. Used to display alerts.
 */
function AlertController() {
	return [ '$scope', 'alertService', function($scope, alertService) {
		$scope.alertService = alertService;
	} ];
}
