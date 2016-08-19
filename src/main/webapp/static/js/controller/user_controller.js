'use strict';

angular.module('myApp').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
	
	var self = this;
	self.user = {id:null, username:'', address:'', email:''};
	self.users = [];
	
	self.submit = submit;
	self.edit = edit;
	
	
	fetchAllUsers();
	
	function fetchAllUsers() {
		UserService.fetchAllUsers()
			.then(
					function(d) {
						self.users = d;
					},
					function(errResponse) {
						console.error('Error while fetching Users');
					}
			);
	}
	
	function edit(id) {
		console.log('id to be edited ', id);
		for (var i = 0; i < self.users.length; i++) {
			if (self.users[i].id === id) {
				self.user = angular.copy(self.users[i]);
				break;
			}
		}
	}
	
	function createUser(user) {
		UserService.createUser(user)
			.then(
					fetchAllUsers,
					function(errResponse) {
						console.error('Erro while creating User');
					}
			);
	}
	
	function submit() {
		if (self.user.id === null) {
			console.log('Saving New User', self.user);
			createUser(self.user);
		}
	}
	
	function reset() {
		self.user = {id:null, username:'', address:'', email:''};
		$scope.myForm.$setPristine(); // reset Form
	}
	
}]);