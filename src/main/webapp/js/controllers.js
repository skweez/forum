'use strict';

/*
 * TODO (mks) Is there any way we can split this up? The file is not too long
 * just yet but it already contains different concerns like discussions and
 * log-in. I have the impression that this might get very large and confusing
 * over time. Maybe have a file for each of the controller function objects and
 * only instantiate them here?
 * 
 * Like a DiscussionController.js with a function object named
 * DiscussionController and using it with:
 * controllersModule.controller('DiscussionController', […, new
 * DiscussionController(…)]);
 */

// TODO (mks) Could we reduce nesting depth further by extracting functions?
/* Controllers */

var controllersModule = angular.module('net.skweez.forum.controllers', [
		'ngResource', 'ngCookies' ]);

/**
 * Displays a list of discussions.
 */
controllersModule
		.controller(
				'DiscussionsOverviewController',
				[
						'$scope',
						'$http',
						'$resource',
						'Discussions',
						'AlertService',
						'UserService',
						// TODO (mks) Are these some kind of "magic" names?
						// Would it be possible that they use the standard
						// naming, i.e. scope, http, alertService, etc.?
						function($scope, $http, $resource, Discussions,
								AlertService, UserService) {
							$scope.discussions = Discussions.query();

							$scope.showNewDiscussionDialog = function() {
								if (!UserService.isLoggedIn) {
									AlertService
											.addAlert({
												type : "warning",
												title : "",
												content : "You need to be logged in to create a new Discussion."
											});
								} else {
									$('#newDiscussion').modal('show');
								}
							};

							// Listen for 'discussionAdded' broadcast and update
							// view
							$scope.$on('discussionAdded',
									function(event, args) {
										$http.get(args.location).success(
												function(discussion) {
													$scope.discussions
															.push(discussion);
												});
									});
						} ]);

/**
 * The NewDiscussionController. Used to create a new discussion with on initial
 * post.
 */
controllersModule.controller('NewDiscussionController', [
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
						posts : {
							'Post' : {
								content : $scope.content
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

/**
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
				Posts.save({
					discussionId : $routeParams.discussionId
				}, {
					'Post' : $scope.newPost
				}, function() {
					// save success function: add new post to displayed posts
					var post = angular.copy($scope.newPost);
					// TODO (elm): Can we assume now() is close enough to the date the servers sets?
					post.date = new Date();
					$scope.posts.push(post);
					// empty the new post box
					$scope.newPost.content = "";
				});
			};
		} ]);

/**
 * The LoginController. Logs a user in and out and displays the name of the
 * logged in user.
 */
controllersModule.controller('LoginController', [
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

		} ]);

/*
 * Alert controller. Used to display alerts.
 */
controllersModule.controller('AlertController', [ '$scope', 'AlertService',
		function($scope, AlertService) {
			$scope.alertService = AlertService;
		} ]);
