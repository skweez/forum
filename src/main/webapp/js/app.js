'use strict';


// Declare app level module which depends on filters, and services
angular.module('net.skweez.forum', ['net.skweez.forum.filters', 'net.skweez.forum.services', 'net.skweez.forum.directives', 'net.skweez.forum.controllers', '$strap.directives']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/discussions', {templateUrl: 'partials/discussions.html', controller: 'DiscussionsContoller'});
    $routeProvider.otherwise({redirectTo: '/discussions'});
  }]);
