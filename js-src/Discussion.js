goog.require('skweez.forum.Utils')

// The discussions view model
function DiscussionsViewModel() {
	var self = this;
	self.discussionsURI = '/api/discussions';
	self.discussions = ko.observableArray();

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
							title : ko.observable(discussion.title)
						});
					});
				});
	}

	// Get all discussions from the server
	nsfAjax(self.discussionsURI, 'GET').done(function(discussions) {
		if (discussions) {
			for (var i = 0; i < discussions.length; i++) {
				self.discussions.push({
					id : ko.observable(discussions[i].id),
					title : ko.observable(discussions[i].title)
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
				posts : [ {
					'Post' : {
						content : self.content(),
						date : new Date()
					}
				} ]
			}
		});
		self.title("");
		self.content("");
	}
}
