'use strict';

/* Services */

angular.module('net.skweez.forum.services', [ 'ngResource' ])
  .factory('Discussions', ['$resource', function($resource) {// The discussions service. Can be injected as "Discussions".
	  return $resource('/api/discussions/:discussionId');
  }]);
