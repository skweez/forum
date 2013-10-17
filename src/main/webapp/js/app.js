'use strict';


// Declare app level module which depends on filters, and services
angular.module('net.skweez.forum', ['net.skweez.forum.filters', 'net.skweez.forum.services', 'net.skweez.forum.directives', 'net.skweez.forum.controllers', '$strap.directives', 'ngSanitize']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/discussions', {templateUrl: 'partials/discussions.html', controller: 'DiscussionsContoller'});
    // TODO (mks) Should this also be plural /discussions/… analogous to the API?
    $routeProvider.when('/discussion/:discussionId', {templateUrl: 'partials/discussion.html', controller: 'DiscussionController'});
    $routeProvider.otherwise({redirectTo: '/discussions'});
  }]);
