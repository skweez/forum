function UserViewModel() {
	var self = this;
	self.userAPI = '/api/user/';
	self.name = ko.observable();
	self.password = ko.observable();

	self.login = function() {
		var request = {
			url : self.userAPI + 'login',
			type : 'GET',
			cache : false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Authorization", "Basic "
						+ btoa(self.name() + ":" + self.password()));
			},
			statusCode : {
				401 : function() {
					nsfShowAlert("Wrong username or password supplied", "alert-warning");
				},
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ajax error: " + jqXHR.status + " " + textStatus
						+ " " + errorThrown);
			}
		};
		$.ajax(request).done(function(data, textStatus, jqXHR) {
			$('#login-dropdown-toggle').dropdown('toggle');
			nsfShowAlert("You are logged in", "alert-success");
		});
	};

	self.logout = function() {
		var request = {
			url : self.userAPI + 'logout',
			type : 'GET',
			cache : false,
		};
		$.ajax(request);
	};
}

var userViewModel = new UserViewModel();

ko.applyBindings(userViewModel, $('#navbar')[0]);