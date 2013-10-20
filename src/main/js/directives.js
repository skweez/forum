goog.provide('net.skweez.forum.directives');

goog.require('net.skweez.forum.app');
goog.require('net.skweez.forum.MarkdownDirective');

var directives = angular.module('net.skweez.forum.directives', []);

directives.directive('markdown', MarkdownDirective());
