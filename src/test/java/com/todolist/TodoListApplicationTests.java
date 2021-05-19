package com.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todolist.dao.TaskRepository;
import com.todolist.dao.UserRepository;
import com.todolist.model.User;

@SpringBootTest
class TodoListApplicationTests {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TaskRepository taskRepo;
	
	@Test
	void testCreateOrRegisterUser() {
		String username ="testUser";
		String email = "testuser@test";
		String password = "123456";
		long userId = userRepo.create(username, email, password);
		
		assertNotNull(userRepo.findByEmailAndPassword(email, password));
	}
	

}
