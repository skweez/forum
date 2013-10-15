'use strict';

/* Directives */

var directives = angular.module('net.skweez.forum.directives', []);

/**
 * Defines a "markdown" attribute with a "content" attribute. The "content" will
 * be rendered with markdown.js.
 * 
 * Example: <div markdown content="*this* is markdown"></div> or: <div markdown
 * content="{{someController.content}}"></div> to render text from a controller
 */
directives.directive('markdown', function($filter) {
	return {
		link : function(scope, element, attrs) {
			attrs.$observe("content", function(content) {
				if (typeof content !== 'undefined') {
					// needs markdown.js
					var tree = markdown.parse(content);

					// recursively apply linkifyTree to the JsonML tree
					(function linkifyTree(part, index, tree) {
						// part == tree[index]
						// the first element is the descriptor of the (sub-)tree
						// so we ignore it.
						if (index == 0)
							return;
						// if we find a string we linkify it
						if (typeof part === 'string') {
							// rebuild string from scratch
							tree[index] = "";
							linkify(part, {
								callback : function(text, href) {
									// href is defined if a link is found
									if (href) {
										// add a new Subarray to the tree with a
										// link object that contains the href as
										// link and as title
										var newTreeElement;
										if (href.search(/\.(jpg|jpeg|png|gif)$/) != -1) {
											newTreeElement = [ "img", {
												href: href}
											];
										} else {
											newTreeElement = [ "link", {
												href : href
											}, href ];
										}
										
										tree.splice(index + 1, 0, newTreeElement);
										// add an empty string after that
										// object. All the text that was in
										// tree[index] before the linkification
										// goes now here.
										tree.splice(index + 2, 0, "");
										// every text that is following the link
										// should go in the string following the
										// link object
										index = index + 2;
									} else {
										// if no link is found we simply add the
										// text back to the tree.
										tree[index] = tree[index] + text;
									}
								}
							});
						}
						// call linkifyText for all subarrays.
						if (Array.isArray(part)) {
							part.forEach(linkifyTree);
						}
					})(tree, null, null);
					// render the tree and put display it
					element.html(markdown.renderJsonML(markdown.toHTMLTree(tree)));
				}
			});
		}
	};
});