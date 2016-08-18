'use strict';

angular.module('myApp').factory('UserService', ['$http', '$q', function($http, $q) {
	
	var REST_SERVICE_URI = "http://localhost:8081/Spring4AngularJS/user/";
	
	var factory = {
			fetchAllUsers: fetchAllUsers
	};
	
	return factory;
	
	function fetchAllUsers() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI)
			.then(
					function(response) {
						deferred.resolve(response.data);
					},
					function(errReponse) {
						console.error('Error while fetching Users');
						deferred.reject(errReponse);
					}
			);
		return deferred.promise;
	}
	
}]);