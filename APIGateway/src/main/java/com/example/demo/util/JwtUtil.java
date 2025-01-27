package com.example.demo.util;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	
	private static final String SECRET = "5d41402abc4b2a76b9719d911017c5925d41402abc4b2a76b9719d911017c592";

		public void validateToken(final String token) {
			 Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		}
	

	private Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
