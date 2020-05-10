package com.stackroute.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.demo.model.User;
import com.stackroute.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public boolean addUser(User user) {
		try {
			userRepository.save(user);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean validate(String email, String password) {
	
		if(userRepository.validate(email, password) != null ) {
			return true;
		}else {
			return false;
		}
	
	
	}
	@Override
	public boolean deleteUser(String email) {
		try {
	userRepository.deleteById(email);
	return true;
	}catch(Exception f) {
		return false;
	}
	}
	
	@Override
	public boolean updateProfile(User user) {
		try {
			if(!userRepository.findById(user.getEmail()).isEmpty()) {
				userRepository.deleteById(user.getEmail());
				userRepository.save(user);
				return true;
			}
			return false;
		}catch(Exception g) {
			return false;
		}
	}
	
	
	
	@Override
	public boolean updatePassword(User user) {
		try {
			if(!userRepository.findById(user.getEmail()).isEmpty()) {          
				userRepository.deleteById(user.getEmail());
				userRepository.save(user);
				return true;
			}
			return false;
		}catch(Exception h) {
			return false;
		}
	}
	
}
