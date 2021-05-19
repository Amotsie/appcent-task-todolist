package com.todolist.service;

import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dao.UserRepository;
import com.todolist.exceptions.EtAuthException;
import com.todolist.model.User;

@Service
@Transactional
public class UserService implements IUserService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public User validateUser(String email, String pawssword) throws EtAuthException {
		if(email != null)
			email = email.toLowerCase();
		return userRepo.findByEmailAndPassword(email, pawssword);
	}

	@Override
	public User registerUser(String username, String email, String password) throws EtAuthException {
		if(username.length()<3 || password.length()<4)
			throw new EtAuthException("Ivalid registration parameters, Username or Password too short");
		Pattern emailPatt = Pattern.compile("^(.+)@(.+)$");
		if(email != null) email= email.toLowerCase();
		if(!emailPatt.matcher(email).matches())
			throw new EtAuthException("Ivalid Email format");
		if(userRepo.getEmailCount(email) > 0)
			throw new EtAuthException("Email already in Use");
		Long userId = userRepo.create(username, email, password);
 		return userRepo.findById(userId);
	}

}
