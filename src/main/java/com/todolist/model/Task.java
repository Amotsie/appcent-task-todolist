package com.todolist.model;

import javax.persistence.*;
import javax.validation.constraints.*;

//@Entity(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Min(value = 3)
	private String description;
	
	private Boolean complete;
	
	private long userid;
	
	public Task() {}

	public Task(long id, @Min(3) String description, @NotNull Boolean complete, long userId) {
		super();
		this.id = id;
		this.description = description;
		this.complete = complete;
		this.userid = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public long getUserId() {
		return userid;
	}

	public void setUserId(long userId) {
		this.userid = userId;
	}
	
	
}
