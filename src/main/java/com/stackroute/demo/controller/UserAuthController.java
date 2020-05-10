package com.stackroute.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.demo.model.User;
import com.stackroute.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserAuthController {
	
	long Expirationtime = 300000;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("adduser")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		userService.addUser(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody User user) {
	
		String jwtToken = "";
		Map<String,String> map = new HashMap<>();
		
		try {
			
			jwtToken = getToken(user.getEmail(),user.getPassword());
			map.clear();
			map.put("message", "User logged in successfully");
			map.put("Token",jwtToken);
			
		}catch(Exception e) {
			map.clear();
			map.put("message",e.getMessage());
			map.put("Token",null);
		}
		userService.addUser(user);
		return new ResponseEntity<>(map,HttpStatus.OK);
	
	}
	
	public String getToken(String email,String password) throws Exception {
		
		
		if(email == null || password == null) {
			throw new ServletException("Please fill the username and password");
		}
		
		boolean status = userService.validate(email,password);
		
		if(!status) {
			throw new ServletException("Invalid credentials");
		}
		
		String jwtToken = Jwts.builder()
							.setSubject(email)
							.setIssuedAt(new Date())
							.setExpiration(new Date(System.currentTimeMillis()+Expirationtime))
							.signWith(SignatureAlgorithm.HS256, "secretKey")
							.compact();
		return jwtToken;
	}

	
	@GetMapping("allusers")
	public ResponseEntity<List<User>> getAllbUsers() {
		
		return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<User>deleteUser(@RequestParam(name="email") String email){
		userService.deleteUser(email);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	@PutMapping("password")
	public ResponseEntity<User>updatePassword(@RequestBody User user){
		userService.updatePassword(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	@PutMapping("changeProfile")
	public ResponseEntity<User>updateProfileImage(@RequestBody User user){
		userService.updateProfile(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	
	
	@GetMapping("/api/v1/user/allusers")
	public ResponseEntity<List<User>> getAllaUsers() {
		
		return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
	}
}
