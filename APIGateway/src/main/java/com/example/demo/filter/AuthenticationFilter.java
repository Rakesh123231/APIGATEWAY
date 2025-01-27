package com.example.demo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.util.JwtUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator routeValidator;
	
	/*
	 * @Autowired private RestTemplate restTemplate;
	 */

	@Autowired
	private JwtUtil jwtUtil;
	public static class Config {

	}

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		return ((exchange, chain) -> {
			if (routeValidator.isSecured.test(exchange.getRequest())) {
				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

					throw new RuntimeException("Missing Authorization Header");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				
				if (authHeader!= null && authHeader.startsWith("Bearer ")) {
					authHeader.substring(7);
					
				}
				try {
					//REST CALL to Auth Service
//					restTemplate.getForObject("http://IDENTITY-SERVICE/validate?token"+authHeader, String.class);
					
					jwtUtil.validateToken(authHeader);
				}catch(Exception ex) {
					
					System.out.println("Invalid Access");
					throw new RuntimeException("UnAuthorized Access to Application");
				}
			}

			return chain.filter(exchange);
		});
	}
}
