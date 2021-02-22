package com.proyect.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.entity.Session;
import com.proyect.repository.SessionRepósitory;

@Service
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionRepósitory repo;

	

	@Override
	public Session createOrUpdate(Session s) {
		
		return repo.save(s);
	}

	@Override
	public Session deleteSession(String token) {
		var sessionOp = repo.findByToken(token);
		var calendar = Calendar.getInstance();
		
		if(sessionOp.isPresent() && sessionOp!=null)
		{
			var session = sessionOp.get();
			
			System.out.println("Encontré session");
			session.setStatus("I");
			session.setExpiresdAt(calendar.getTime());
			
			var response = repo.save(session);
			System.out.println(response.getStatus());
			return response;
			
		}
		else
		{
			System.out.println("Sali session");
			return null;
		}
		
	}

	@Override
	public Optional<Session> findLastSessionByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return repo.findLastSessionByUserId(userId);
	}

	@Override
	public Optional<Session> findByToken(String token) {
		// TODO Auto-generated method stub
		return repo.findByToken(token);
	}

}
