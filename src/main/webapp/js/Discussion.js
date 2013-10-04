// TODO (mks) Move DiscussionsViewModel and CreateDiscussionsViewModel to their own files?

// The discussions view model
function DiscussionsViewModel() {
	var self = this;
	self.discussionsURI = '/api/discussions';
	self.discussions = ko.observableArray();

	/*
	 * TODO (mks) I think beginCreateNewDiscussion is a function specific to the
	 * page, not to the view-model. Move to HTML? Or should we have a .js for
	 * each .html with page-specific code? Like an index.js for index.html, etc.
	 */

	// Show new discussion dialog
	self.beginCreateNewDiscussion = function() {
		$('#newDiscussion').modal('show');
	}

	// Add new discussion
	self.addDiscussion = function(discussion) {
		nsfAjax(self.discussionsURI, 'POST', discussion).done(
				function(data, textStatus, jqXHR) {
					$.getJSON(jqXHR.getResponseHeader("Location"), function(
							discussion) {
						self.discussions.push({
							id : ko.observable(discussion.id),
							title : ko.observable(discussion.title),
							date : ko.observable(discussion.date)
						});
					});
				});
	}

	// Get all discussions from the server
	nsfAjax(self.discussionsURI, 'GET').done(function(discussions) {
		if (discussions) {
			for ( var i = 0; i < discussions.length; i++) {
				self.discussions.push({
					id : ko.observable(discussions[i].id),
					title : ko.observable(discussions[i].title),
					date : ko.observable(discussions[i].date)
				});
			}
		}
	});
}

function CreateDiscussionsViewModel() {
	var self = this;
	self.title = ko.observable();
	self.content = ko.observable();

	self.createDiscussion = function() {
		$('#newDiscussion').modal('hide');
		discussionsViewModel.addDiscussion({
			'Discussion' : {
				title : self.title(),
				date : new Date(),
				posts : [ {
					'Post' : {
						content : self.content().replace(/\n/g, '<br/>'),
						date : new Date()
					}
				} ]
			}
		});
		self.title("");
		self.content("");
	}
}
