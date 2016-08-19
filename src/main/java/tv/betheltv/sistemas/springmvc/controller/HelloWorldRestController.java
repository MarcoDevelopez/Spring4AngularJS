package tv.betheltv.sistemas.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import tv.betheltv.sistemas.springmvc.model.User;
import tv.betheltv.sistemas.springmvc.service.UserService;

@RestController
public class HelloWorldRestController {

	@Autowired
	UserService		userService;
	
	// Retrieve All Users
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	// Retrieve Single User
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		System.out.println("FETCHING USER WITH ID " + id);
		User user = userService.findById(id);
		if (user == null) {
			System.out.println("USER WITH ID " + id + " NOT FOUND");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// Create a User
	 @RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User prmUser, UriComponentsBuilder prmBuilder) {
		System.out.println("CREATING USER " + prmUser.getUsername());
		
		if (userService.isUserExist(prmUser)) {
			System.out.println("A USER WITH NAME " + prmUser.getUsername() + " ALREADY EXIST");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		userService.saveUser(prmUser);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(prmBuilder.path("/user/{id}").buildAndExpand(prmUser.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	 
	// Update User
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User prmUser) {
		System.out.println("UPDATING USER " + id);
		User currentUser = userService.findById(id);
		
		if (currentUser == null) {
			System.out.println("USER WITH ID " + id + " NOT FOUND");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		currentUser.setUsername(prmUser.getUsername());
		currentUser.setAddress(prmUser.getAddress());
		currentUser.setEmail(prmUser.getEmail());
		
		userService.updateUser(prmUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	// Delete a User
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		System.out.println("FETCHING & DELETING USER WITH ID " + id);
		
		User user = userService.findById(id);
		if (user == null) {
			System.out.println("UNABLE TO DELETE. USER WITH ID " + id + " NOT FOUND");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
}
