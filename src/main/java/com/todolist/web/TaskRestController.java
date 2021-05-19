package com.todolist.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.model.Task;
import com.todolist.service.TaskService;

@RestController
@RequestMapping("/api/todos")
public class TaskRestController {
	
	@Autowired
	TaskService taskService;
	
	@GetMapping 
	public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest req) {
		long userId = Long.parseLong(req.getAttribute("userId").toString());
		List<Task> tasks = taskService.fetchAllTasks(userId);
		if(tasks.size() == 0 || tasks ==null)
			return new ResponseEntity<>(tasks, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(tasks, HttpStatus.ACCEPTED);
	}
	
	@PostMapping
	public ResponseEntity<Task> addTask(HttpServletRequest req, @RequestBody Map<String, Object> taskMap) {
		long userId = Long.parseLong(req.getAttribute("userId").toString());
		String description = (String)taskMap.get("description");
		Task task = taskService.addTask(userId, description);
		
		return new ResponseEntity<>(task, HttpStatus.CREATED);
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTaskById(HttpServletRequest req, @PathVariable("taskId") long taskId) {
		long userId = Long.parseLong(req.getAttribute("userId").toString());
		Task task = taskService.fetchTaskById(userId, taskId);
		if(task == null)
			return new ResponseEntity<>("Task not found!", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(task, HttpStatus.CREATED);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<?> updateTask(HttpServletRequest req,@PathVariable("taskId") long taskId, @RequestBody Task taskMap) {
		
		long userId = Long.parseLong(req.getAttribute("userId").toString());
		
		Task task = taskService.fetchTaskById(userId, taskId);
		
		String description = taskMap.getDescription();
		Boolean isComplete = taskMap.getComplete();
		
		if(description == null && isComplete == null ) {
			return new ResponseEntity<>("Invalid parameters",HttpStatus.BAD_REQUEST);
		}
		else if(description != null && isComplete == null) {
			taskService.updateTask(userId, taskId, description, task.getComplete());
			return new ResponseEntity<>("Task Update Successfull",HttpStatus.ACCEPTED);
		}
		else if(description == null && isComplete != null) {
			taskService.updateTask(userId, taskId, task.getDescription(), isComplete);
			return new ResponseEntity<>("Task Update Successfull",HttpStatus.ACCEPTED);
		}
		
		else{;
			taskService.updateTask(userId, taskId, description, isComplete);
			return new ResponseEntity<>("Task Update Successfull",HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<?> deleteTask(HttpServletRequest req,@PathVariable("taskId") long taskId){
		long userId = Long.parseLong(req.getAttribute("userId").toString());
		taskService.removeTask(userId, taskId);
		return new ResponseEntity<>("Task Delete Successfull",HttpStatus.OK);
	}
	
}
