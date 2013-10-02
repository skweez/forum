function UserViewModel() {
	var self = this;
	self.loginAPI = '/api/login'
	self.name = ko.observable();
	self.password = ko.observable();

	self.login = function() {
		var request = {
			url : self.loginAPI,
			type : 'POST',
			cache : false,
			data : {
				name : self.name(),
				password : self.password()
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ajax error: " + jqXHR.status + " " + textStatus
						+ " " + errorThrown);
			}
		};
		$.ajax(request).done(function(data, textStatus, jqXHR) {
			alert("You are logged in");
		});
	}
}

var userViewModel = new UserViewModel();

ko.applyBindings(userViewModel, $('#navbar')[0]);