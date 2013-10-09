'use strict';

/* Controllers */

var controllersModule = angular.module('net.skweez.forum.controllers', [
		'ngResource', 'ngCookies' ]);

controllersModule.controller('DiscussionsContoller', [ '$scope', '$http',
		'$resource', 'Discussions', // This is
		// our own Discussion service defined in services.js
		function($scope, $http, $resource, Discussions) {
			$scope.discussions = Discussions.query();

			$scope.showNewDiscussionDialog = function() {
				$('#newDiscussion').modal('show');
			};

			// Listen for 'discussionAdded' broadcast and update view
			$scope.$on('discussionAdded', function(event, args) {
				$http.get(args.location).success(function(discussion) {
					$scope.discussions.push(discussion);
				});
			});
		} ]);

controllersModule.controller('NewDiscussionsContoller', [
		'$scope',
		'$http',
		'$rootScope',
		'Discussions',
		function($scope, $http, $rootScope, Discussions) {
			$scope.title = null;
			$scope.content = null;

			$scope.createDiscussion = function() {
				var newDiscussion = new Object({
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
				Discussions.save(newDiscussion, function(data,
						getResponseHeaders) {
					$('#newDiscussion').modal('hide');
					$rootScope.$broadcast('discussionAdded', {
						'location' : getResponseHeaders('location')
					});
				});
			};
		} ]);

controllersModule.controller('LoginController', [
		'$scope',
		'$http',
		'$cookies',
		function($scope, $http, $cookies) {
			$scope.uid = $cookies.uid;
			$scope.password = null;
			$scope.loggedIn = false;

			if ($scope.uid != null) {
				$http.get('/api/user/login').success(function() {
					$scope.loggedIn = true;
				});
			}

			$scope.login = function() {
				$http.get(
						'/api/user/login',
						{
							headers : {
								'Authorization' : "Basic "
										+ btoa($scope.uid + ":"
												+ $scope.password)
							}
						}).success(function() {
					$('#login-dropdown-toggle').dropdown('toggle');
					$scope.loggedIn = true;
				}).error(function() {
					console.log("wrong username or password");
				});
			};

			$scope.logout = function() {
				$http.get('/api/user/logout');
				$scope.loggedIn = false;
			};

		} ]);
