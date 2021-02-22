package com.proyect.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyect.entity.Session;

@Service
public interface SessionService {
	
	Optional<Session> findLastSessionByUserId(Integer userId);
	Session createOrUpdate(Session s);
	Session deleteSession(String token);
	Optional<Session> findByToken(String token);
	

}
