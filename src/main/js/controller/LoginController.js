goog.provide('net.skweez.forum.controller.LoginController');

goog.require('net.skweez.forum.services');

/**
 * The LoginController. Logs a user in and out and displays the name of the
 * logged in user.
 */
function LoginController() {
	return [
			'$scope',
			'$http',
			'$cookies',
			'AlertService',
			'UserService',
			function($scope, $http, $cookies, AlertService, UserService) {
				$scope.userService = UserService;
				$scope.password = null;
				$scope.stayloggedin = false;

				// If we have a uid cookie we check if we are still logged in
				if ($cookies.uid != null) {
					$http.get('/api/user/login').success(function() {
						UserService.uid = $cookies.uid;
						UserService.isLoggedIn = true;
					});
				}

				$scope.login = function() {
					$http.get(
							'/api/user/login',
							{
								headers : {
									'Authorization' : "Basic "
											+ btoa(UserService.uid + ":"
													+ $scope.password)
								},
								params : {
									'stayloggedin' : $scope.stayloggedin
								}
							}).success(function() {
						$('#login-dropdown-toggle').dropdown('toggle');
						UserService.isLoggedIn = true;
					}).error(function() {
						AlertService.addAlert({
							"type" : "danger",
							"title" : "Login failure:",
							"content" : "Wrong username or passowrd supplied."
						});
						console.log("wrong username or password");
					});
				};

				$scope.logout = function() {
					$http.get('/api/user/logout');
					UserService.uid = null;
					UserService.isLoggedIn = false;
				};

			} ];
}