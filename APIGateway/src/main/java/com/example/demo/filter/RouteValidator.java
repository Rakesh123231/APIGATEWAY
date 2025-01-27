package com.example.demo.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	
	public static final List<String> openApiEndPoints = List.of(
			"/auth/register",
			"/auth/token",
			"/eureka"
			);
			
			/*"/auth/register",
			"/auth/authenticate",
			"/eureka",
			"/api-docs"*/
	

	public Predicate <ServerHttpRequest> isSecured = request -> openApiEndPoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
