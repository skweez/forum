goog.provide('net.skweez.forum.services');

goog.require('net.skweez.forum.app');
goog.require('net.skweez.forum.service.DiscussionsService');
goog.require('net.skweez.forum.service.PostsService');
goog.require('net.skweez.forum.service.AlertService');
goog.require('net.skweez.forum.service.UserService');

var services = angular.module('net.skweez.forum.services', [ 'ngResource' ]);

services.factory('discussions', DiscussionsService());
services.factory('posts', PostsService());
services.service('alertService', AlertService());
services.factory('userService', UserService());
