goog.provide('net.skweez.forum.AlertService');

/**
 * The AlertService. Can be injected to show alerts.
 * 
 * Usage: pass an object to addAlert with the following attributes:
 * 	type: the bootstrap alert type. Available values: info, success, warning, danger
 * 	title: the title of the alert (will be displayed in bold)
 * 	content: the content of the alert
 */
function AlertService() {
	return [ function() {
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
	} ];
}