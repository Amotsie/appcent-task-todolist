package com.todolist.dao;

import java.util.List;

import com.todolist.exceptions.EtBadRequestException;
import com.todolist.exceptions.EtResourceNotFoundException;
import com.todolist.model.Task;

public interface ITaskRepository {
	
	List<Task> findAllTasks(long userId)throws EtResourceNotFoundException;
	
	Task findTaskById(long userId, long taskId) throws EtResourceNotFoundException;
	
	int createTask(long userId, String description)throws EtBadRequestException;//bolean complete is False by default;
	
	void updateTask(long userId, long taskId, String description, boolean isComplete)throws EtBadRequestException;
	
	void removeTaskById(long userId, long categoryId);
}
