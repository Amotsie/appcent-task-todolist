package com.todolist.service;

import java.util.List;

import com.todolist.exceptions.EtBadRequestException;
import com.todolist.exceptions.EtResourceNotFoundException;
import com.todolist.model.Task;

public interface ITaskService {
	
	List<Task> fetchAllTasks(long userId);
	
	Task fetchTaskById(long userId, long taskId) throws EtResourceNotFoundException;
	
	Task addTask(long userId, String description)throws EtBadRequestException;//bolean complete is False by default;
	
	void updateTask(long userId, long taskId, String description, boolean isComplte)throws EtBadRequestException;
	
	void removeTask(long userId, long categoryId) throws EtResourceNotFoundException;
}
