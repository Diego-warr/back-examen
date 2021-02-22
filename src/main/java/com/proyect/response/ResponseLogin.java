package com.proyect.response;

import java.io.Serializable;

import com.proyect.entity.Session;
import com.proyect.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin implements Serializable {
	
	private User user;
	private Session session;
	
	

}
