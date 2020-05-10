package com.stackroute.demo.service;

import java.util.List;

import com.stackroute.demo.model.User;

public interface UserService {
	public List<User> getAllUsers();
	public boolean addUser(User u);
	public boolean validate(String email,String password);
    public boolean deleteUser(String email);
    public boolean updatePassword(User user);
    public boolean updateProfile(User user);
}
