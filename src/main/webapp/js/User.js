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
			beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 
                    "Basic " + btoa(self.name() + ":" + self.password()));
            },
            statusCode: {
            	401:function() { alert("Wrong username or password supplied"); },
            },
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ajax error: " + jqXHR.status + " " + textStatus
						+ " " + errorThrown);
			}
		};
		$.ajax(request).done(function(data, textStatus, jqXHR) {
			alert("You are logged in");
			localStorage.setItem("AuthCode" , btoa(self.name() + ":" + self.password()));
		});
	};
	
	self.logout = function() {
		localStorage.removeItem("AuthCode");
		
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