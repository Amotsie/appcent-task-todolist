package com.todolist.dao;

import java.util.List;

import com.todolist.exceptions.EtAuthException;
import com.todolist.model.User;

public interface IUserRepository {
//	public List<User> findAll();

	User findById(long useId);
	User findByEmailAndPassword(String email, String Pawssword) throws EtAuthException;	
	long create(String username, String email, String password) throws EtAuthException;
	
	Integer getEmailCount(String email);
//	public void delete(long id);

//	public void update(User user);
}
