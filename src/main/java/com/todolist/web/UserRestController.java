package com.todolist.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.Constants;
import com.todolist.model.User;
import com.todolist.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> registerUser(@Valid @RequestBody User userMap,BindingResult result) {
		if(result.hasErrors()) {
			
			Map<String,String> map = new HashMap<>();
			result.getAllErrors().forEach(res->{
				map.put(res.getObjectName(), res.getDefaultMessage());
			});
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		String username = userMap.getUsername();
		String email = userMap.getEmail();
		String password =userMap.getPassword();
		
		User user = userService.registerUser(username, email, password);
		Map<String,String> map = generateJWTToken(user);
		map.put("message", "User/Account creation successfull");
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String, Object> userMap) {
		String email = (String) userMap.get("email");
		String password =(String) userMap.get("password");
		
		User user = userService.validateUser(email, password);
		
		Map<String,String> map = generateJWTToken(user);
		map.put("message", "Log in successfull");
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	public Map<String,String> generateJWTToken(User user) {
		long timeStamp = System.currentTimeMillis();
		String token = Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, Constants.API_SECRETE_KEY)
				.setIssuedAt(new Date(timeStamp))
				.setExpiration(new Date(timeStamp + Constants.TOKEN_VALIDITY))
				.claim("userId", user.getId())
				.claim("email", user.getEmail())
				.compact();
		Map<String,String> map = new HashMap<>();
		map.put("token", token);
		
		return map;
	}
}
