'use strict';

/* Controllers */

var controllersModule = angular.module('net.skweez.forum.controllers', [
		'ngResource', 'ngCookies' ]);

/*
 * The DiscussionsController. Used to display a list of discussions.
 */
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

/*
 * The NewDiscussionsController. Used to create a new discussion with on initial
 * post.
 */
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

/*
 * The DiscussionController. Used to control single discussions.
 */
controllersModule.controller('DiscussionController', [ '$scope',
		'$routeParams', 'Discussions', 'Posts',
		function($scope, $routeParams, Discussions, Posts) {
			$scope.newPost = {};

			// Get current discussion
			$scope.discussion = Discussions.get({
				discussionId : $routeParams.discussionId
			});

			// Get all the posts for this discussion
			$scope.posts = Posts.query({
				discussionId : $routeParams.discussionId
			});

			// Create a new post
			$scope.createPost = function() {
				$scope.newPost.date = new Date();
				Posts.save({
					discussionId : $routeParams.discussionId
				}, {
					'Post' : $scope.newPost
				}, function() {
					// save success function: add new post to displayed posts
					$scope.posts.push(angular.copy($scope.newPost));
					// empty the new post box
					$scope.newPost.content = "";
				});
			};
		} ]);

/*
 * The LoginController. Logs a user in and out and displays the name of the
 * logged in user.
 */
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
