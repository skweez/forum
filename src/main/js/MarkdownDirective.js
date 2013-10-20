goog.provide('net.skweez.forum.MarkdownDirective');

/**
 * Defines a "markdown" attribute with a "content" attribute. The "content" will
 * be rendered with markdown.js.
 * 
 * Example: <div markdown content="*this* is markdown"></div> or: <div markdown
 * content="{{someController.content}}"></div> to render text from a controller
 */
function MarkdownDirective() {
	return [
			'$filter',
			function($filter) {
				/**
				 * Creates a markdown element from a href string. This element
				 * can be added to a markdown.js JsonML tree. If the href string
				 * points to an image, an img (<img> in html) element is
				 * created. Otherwise a link element (<a> in html) is created.
				 * 
				 * @param href
				 *            the href string
				 * @returns a new markdown object
				 */
				function createMarkdownObjectFromLink(href) {
					var markdownObject;
					if (href.search(/\.(jpg|jpeg|png|gif)$/) != -1) {
						markdownObject = [ "img", {
							href : href
						} ];
					} else {
						markdownObject = [ "link", {
							href : href
						}, href ];
					}
					return markdownObject;
				}
				;

				/**
				 * Search for hyper links in a markdown tree and convert them to
				 * richer objects like clickable links or inline images.
				 * 
				 * @param part
				 *            the part of the traversed array
				 * @param index
				 *            the current index
				 * @param tree
				 *            the hole markdown tree
				 */
				function linkifyMarkdownTree(part, index, tree) {
					if (index == 0) {
						return;
					}
					if (typeof part === 'string') {
						tree[index] = "";
						linkify(
								part,
								{
									callback : function(text, href) {
										if (href) {
											var markdownObject = createMarkdownObjectFromLink(href);

											tree.splice(index + 1, 0,
													markdownObject);
											tree.splice(index + 2, 0, "");
											index = index + 2;
										} else {
											tree[index] = tree[index] + text;
										}
									}
								});
					}
					if (Array.isArray(part)) {
						part.forEach(linkifyMarkdownTree);
					}
				}
				;

				return {
					link : function(scope, element, attributes) {
						attributes.$observe("content", function(content) {
							if (typeof content === 'undefined') {
								return;
							}
							var tree = markdown.parse(content);

							linkifyMarkdownTree(tree, null, null);

							element.html(markdown.renderJsonML(markdown
									.toHTMLTree(tree)));

						});
					}
				};
			} ];
}
