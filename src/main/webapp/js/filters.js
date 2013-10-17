'use strict';

/* Filters */

var filters = angular.module('net.skweez.forum.filters', []);

// TODO (mks) Is this still relevant with markdown rendering?
filters.filter('linebreakFilter', function () {
    return function (text) {
        if (text !== undefined) return text.replace(/\n/g, '<br />');
    };
});
