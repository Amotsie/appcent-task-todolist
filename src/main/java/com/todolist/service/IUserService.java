package com.todolist.service;

import com.todolist.exceptions.EtAuthException;
import com.todolist.model.User;

public interface IUserService {
	
	User validateUser(String email, String pawssword) throws EtAuthException;
	User registerUser(String username, String email, String pawssword) throws EtAuthException;
}
