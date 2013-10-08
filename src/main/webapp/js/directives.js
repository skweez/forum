'use strict';

/* Directives */


angular.module('net.skweez.forum.directives', []).
  directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  }]);
