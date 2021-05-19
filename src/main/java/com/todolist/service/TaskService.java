package com.todolist.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dao.TaskRepository;
import com.todolist.exceptions.EtBadRequestException;
import com.todolist.exceptions.EtResourceNotFoundException;
import com.todolist.model.Task;

@Service
@Transactional
public class TaskService implements ITaskService {
	@Autowired
	TaskRepository taskRepo;

	@Override
	public List<Task> fetchAllTasks(long userId) {
		return taskRepo.findAllTasks(userId);
	}

	@Override
	public Task fetchTaskById(long userId, long taskId) throws EtResourceNotFoundException {
		return taskRepo.findTaskById(userId, taskId);
	}

	@Override
	public Task addTask(long userId, String description) throws EtBadRequestException {
		long taskId= taskRepo.createTask(userId, description);
		return taskRepo.findTaskById(userId, taskId);
	}

	@Override
	public void updateTask(long userId, long taskId, String description, boolean isComplete) throws EtBadRequestException {
		taskRepo.updateTask(userId, taskId, description, isComplete);
	}

	@Override
	public void removeTask(long userId, long taskId) throws EtResourceNotFoundException {
		taskRepo.removeTaskById(userId, taskId);
	}

}
