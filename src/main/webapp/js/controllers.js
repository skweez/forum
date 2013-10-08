'use strict';

/* Controllers */

angular
		.module('net.skweez.forum.controllers', [ 'ngResource', 'ngCookies' ])
		.controller(
				'DiscussionsContoller',
				[
						'$scope',
						'$resource',
						function($scope, $resource) {
							var Discussion = $resource('/api/discussions/:discussionId');

							$scope.discussions = Discussion.query();

							$scope.showNewDiscussionDialog = function() {
								$('#newDiscussion').modal('show');
							};
						} ])
		.controller('NewDiscussionsContoller',
				[ '$scope', '$http', function($scope, $http) {
					$scope.title = null;
					$scope.content = null;

					$scope.createDiscussion = function() {
						$http.post('/api/discussions', {
							'Discussion' : {
								title : $scope.title,
								date : new Date(),
								posts : {
									'Post' : {
										content : $scope.content,
										date : new Date()
									}
								}
							}
						});
					};
				} ])
		.controller(
				'LoginController',
				[
						'$scope',
						'$http',
						'$cookies',
						function($scope, $http, $cookies) {
							$scope.uid = $cookies.uid;
							$scope.password = null;
							$scope.loggedIn = false;

							if ($scope.uid != null) {
								$http.get('/api/user/login').success(
										function() {
											$scope.loggedIn = true;
										});
							}

							$scope.login = function() {
								$http
										.get(
												'/api/user/login',
												{
													headers : {
														'Authorization' : "Basic "
																+ btoa($scope.uid
																		+ ":"
																		+ $scope.password)
													}
												})
										.success(
												function() {
													$('#login-dropdown-toggle')
															.dropdown('toggle');
													$scope.loggedIn = true;
												})
										.error(
												function() {
													console
															.log("wrong username or password");
												});
							};

							$scope.logout = function() {
								$http.get('/api/user/logout');
								$scope.loggedIn = false;
							};

						} ]);
