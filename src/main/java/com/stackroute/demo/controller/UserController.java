package com.stackroute.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.demo.model.User;
import com.stackroute.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		
		return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		userService.addUser(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
     public ResponseEntity<User>deleteUser(@RequestParam(name="email") String email){
		userService.deleteUser(email);
		return new ResponseEntity<User>(HttpStatus.OK);
		
	}
	@PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User>updatePassword(@RequestBody User user){
		userService.updatePassword(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	

	
}
