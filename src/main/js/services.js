goog.provide('net.skweez.forum.services');

goog.require('net.skweez.forum.app');
goog.require('net.skweez.forum.DiscussionsService');
goog.require('net.skweez.forum.PostsService');
goog.require('net.skweez.forum.AlertService');
goog.require('net.skweez.forum.UserService');

var services = angular.module('net.skweez.forum.services', [ 'ngResource' ]);

services.factory('Discussions', DiscussionsService());
services.factory('Posts', PostsService());
services.service('AlertService', AlertService());
services.factory('UserService', UserService());
