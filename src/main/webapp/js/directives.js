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
/* TODO (mks) This function has a nesting depth of 10 or so, which is way too
 * high (A nesting depth of 3-4 is often considered good practice).
 * Please try to refactor code into functions with descriptive names.
 */
directives.directive('markdown', function($filter) {
	return {
		link : function(scope, element, attrs) {
			attrs.$observe("content", function(content) {
				if (typeof content !== 'undefined') {
					// TODO (mks) Trivial comment?
					// needs markdown.js
					var tree = markdown.parse(content);

					// TODO (mks) This function barely fits my screen in fullscreen mode. Extract function?
					// recursively apply linkifyTree to the JsonML tree
					(function linkifyTree(part, index, tree) {
						// part == tree[index]
						// the first element is the descriptor of the (sub-)tree
						// so we ignore it.
						if (index == 0)
							// TODO (mks) Please always use {}
							return;
						// TODO (mks) Trivial comment?
						// if we find a string we linkify it
						if (typeof part === 'string') {
							// TODO (mks) Trivial comment?
							// rebuild string from scratch
							tree[index] = "";
							linkify(part, {
								// TODO (mks) Extract function?
								callback : function(text, href) {
									// TODO (mks) Trivial comment?
									// href is defined if a link is found
									if (href) {
										// (TODO) mks The many comments in here do describe what can be seen from the code, but not what is actually done.
										// Can this be extracted to a function with a descriptive name, so we don't need the trivial comments?
										
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
						// TODO (mks) Trivial comment?
						// call linkifyText for all subarrays.
						if (Array.isArray(part)) {
							part.forEach(linkifyTree);
						}
					})(tree, null, null);
					// TODO (mks) Trivial comment?
					// render the tree and put display it
					element.html(markdown.renderJsonML(markdown.toHTMLTree(tree)));
				}
			});
		}
	};
});