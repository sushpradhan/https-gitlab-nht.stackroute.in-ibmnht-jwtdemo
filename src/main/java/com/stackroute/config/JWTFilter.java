package com.stackroute.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String authHeader = req.getHeader("authorization");
		
		if("OPTIONS".equals(req.getMethod())){
			res.setStatus(HttpServletResponse.SC_OK);
			filterchain.doFilter(request, response);
		}else {
			
			if(authHeader == null || !authHeader.startsWith("")) {
				throw new ServletException("Missing or Invalid Authorization header");
			}
			
			String token = authHeader;			
			try {
			final Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
			System.out.println("test2");
			request.setAttribute("claims", claims);
			filterchain.doFilter(request, response);
			}catch(JsonParseException j) {
				System.out.println(j.getMessage());
			}

		}
		
		System.out.println("test3");

		
		

		
	}

}
