package com.proyect.response;

import java.io.Serializable;

import com.proyect.entity.Session;
import com.proyect.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ResponseLogin implements Serializable {
	
	private User user;
	private Session session;
	
	
	public ResponseLogin()
	{
		
	}
	
	


	public ResponseLogin(User user, Session session) {
		
		this.user = user;
		this.session = session;
	}




	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Session getSession() {
		return session;
	}


	public void setSession(Session session) {
		this.session = session;
	}
	
	
	

}
