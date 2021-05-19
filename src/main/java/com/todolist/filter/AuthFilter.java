package com.todolist.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import com.todolist.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Type cast req and res to HTTP
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String authHeader = httpRequest.getHeader("Authorization");
		if(authHeader != null) {
			String[] headers = authHeader.split("Bearer");
			if(headers.length > 1 && headers[1] != null) {
				String token = headers[1];
				try {
					Claims claims = Jwts.parser()
							.setSigningKey(Constants.API_SECRETE_KEY)
							.parseClaimsJws(token)
							.getBody();
					
					httpRequest.setAttribute("userId", claims.get("userId"));
				} catch (Exception e) {
					httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Invalid Token");
					return;
				}
			}else {
				httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Ivalid Token, Authorization headers must be provided as Bearer {token} ");
				return;
			}
			 
		}else {
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Missing auth token. Authorization headers must be provided as Bearer {token}");
			return;
		}
		
		chain.doFilter(httpRequest, httpResponse);
	}

}
