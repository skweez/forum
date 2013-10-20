goog.provide('net.skweez.forum.controllers');

goog.require('net.skweez.forum.app');
goog.require('net.skweez.forum.DiscussionOverviewController');
goog.require('net.skweez.forum.NewDiscussionController');
goog.require('net.skweez.forum.DiscussionController');
goog.require('net.skweez.forum.LoginController');
goog.require('net.skweez.forum.AlertController');

// TODO(elm): Move required modules (e.g. ngResource) to the controller that
// needs it.
var controllersModule = angular.module('net.skweez.forum.controllers', [
		'ngResource', 'ngCookies' ]);

controllersModule.controller('DiscussionsOverviewController',
		DiscussionOverviewController());
controllersModule.controller('NewDiscussionController',
		NewDiscussionController());
controllersModule.controller('DiscussionController', DiscussionController());
controllersModule.controller('LoginController', LoginController());
controllersModule.controller('AlertController', AlertController());
