goog.provide('net.skweez.forum.services');

goog.require('net.skweez.forum.app');
goog.require('net.skweez.forum.DiscussionService');
goog.require('net.skweez.forum.AlertService');
goog.require('net.skweez.forum.UserService');

var services = angular.module('net.skweez.forum.services', [ 'ngResource' ]);

services.factory('Discussions', DiscussionsService());
services.factory('Posts', PostService());
services.service('AlertService', AlertService());
services.factory('UserService', UserService());
