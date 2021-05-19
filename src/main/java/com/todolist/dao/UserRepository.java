package com.todolist.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.Utilities;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.todolist.exceptions.EtAuthException;
import com.todolist.model.User;

@Repository
public class UserRepository implements IUserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private RowMapper<User> rowMapper = new RowMapper<User>() {
		
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
			
		return user;
		}
	};
	

	@Override
	public User findById(long useId) {
		String sql = "select * from todouser where id = ?";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, useId));
	}

	@Override
	public User findByEmailAndPassword(String email, String Password) throws EtAuthException {
		String sql = "SELECT * from todouser WHERE email=? ";
		
		try {
			User user = DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowMapper, email));
			if(!BCrypt.checkpw(Password, user.getPassword()))
				throw new EtAuthException("Invalidemail or password");
			return user;
			
		} catch (Exception e) {
			throw new EtAuthException("Invalidemail or password");
		}
	}

	@Override
	public long create(String username, String email, String password) throws EtAuthException {
		System.out.println("========== USER--REPO CREATE  ===========================");
		String SQL_CREATE = "insert into todouser(username, email, password) values(?,?,?)";
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE,new String[] {"id"});
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, hashedPassword);
				
				return ps;
			},keyHolder);
			return keyHolder.getKey().intValue(); 
			
		} catch (Exception e) {
			throw new EtAuthException("Invalid details, user creation falied.");
		}
				
	}

	@Override
	public Integer getEmailCount(String email) {
		String sql = "SELECT * from todouser WHERE email=?";
		List<User> users = jdbcTemplate.query(sql, rowMapper, email.toLowerCase());
		return users.size();
	}

	

}
