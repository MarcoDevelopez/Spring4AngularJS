'use strict';

angular.module('myApp').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
	
	var self = this;
	self.user = {id:null, username:'', address:'', email:''};
	self.users = [];
	
	self.submit = submit;
	self.edit = edit;
	self.remove = remove;
	self.reset = reset;
	
	
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
	
	function updateUser(user, id) {
		UserService.updateUser(user, id)
			.then(
					fetchAllUsers,
					function(errResponse) {
						console.error('Error while update User');
					}
			);
	}
	
	function deleteUser(id) {
		UserService.deleteUser(id)
			.then(
					fetchAllUsers,
					function(errResponse) {
						console.error('Error while deleting User');
					}
			)
	}
	
	function submit() {
		if (self.user.id === null) {
			console.log('Saving New User', self.user);
			createUser(self.user);
		} else {
			updateUser(self.user, self.user.id);
			console.log('User udpate with id ', self.user.id);
		}
		reset();
	}
	
	function remove(id) {
		console.log('id to be deleted ', id);
		if (self.user.id === id) { // clean form if the user to be deleted is shown there.
			reset();
		}
		deleteUser(id);
	}
	
	function reset() {
		self.user = {id:null, username:'', address:'', email:''};
		$scope.myForm.$setPristine(); // reset Form
	}
	
}]);