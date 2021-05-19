package com.todolist.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.todolist.exceptions.EtBadRequestException;
import com.todolist.exceptions.EtResourceNotFoundException;
import com.todolist.model.Task;

@Repository
public class TaskRepository implements ITaskRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private RowMapper<Task> rowMapper = new RowMapper<Task>() {
		
		@Override
		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		Task task = new Task();
		task.setId(rs.getLong("id"));
		task.setDescription(rs.getString("description"));
		task.setComplete(rs.getBoolean("complete"));
		task.setUserId(rs.getLong("userid"));
		return task;
		}
	};

	@Override
	public List<Task> findAllTasks(long userId) throws EtResourceNotFoundException {
		String sql = "SELECT * FROM TASK  where userid= ?";
		return jdbcTemplate.query(sql, rowMapper, userId);
	}

	@Override
	public Task findTaskById(long userId, long taskId) throws EtResourceNotFoundException {
		String sql = "select * from task where id=? and userid = ?";
		try {
			return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, taskId, userId));
		} catch (Exception e) {
			 throw new EtResourceNotFoundException("Task not found");
		}
		
	}

	@Override
	public int createTask(long userId, String description) throws EtBadRequestException {
		boolean isComplete = false;
		String sql = "insert into task(description, complete, userid) values(?, ?, ?)";
		
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql,new String[] {"id"});
				ps.setString(1, description);
				ps.setBoolean(2, isComplete);
				ps.setLong(3, userId);
				
				return ps;
			},keyHolder);
			return keyHolder.getKey().intValue(); 
			
		} catch (Exception e) {
			throw new EtBadRequestException("Invalid request");
		}
	}

	@Override
	public void updateTask(long userId, long taskId, String description, boolean isComplete) throws EtBadRequestException {
		String sql = "UPDATE TASK SET description=?, complete=? WHERE id=? AND userid=?";
		try {
			jdbcTemplate.update(sql, description, isComplete, taskId, userId);
		} catch (Exception e) {
			throw new EtBadRequestException("Invalid request.");
		}
		
	}

	@Override
	public void removeTaskById(long userId, long taskId) {
		String sql = "delete task where id=? and userid = ?";
		try {
			jdbcTemplate.update(sql, taskId, userId);
		} catch (Exception e) {
			throw new EtBadRequestException("Invalid request.");
		}	
	}

}
