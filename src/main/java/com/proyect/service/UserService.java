package com.proyect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.proyect.entity.User;

@Service
public interface UserService {
	
	public List<User> getAll();
	Optional<User> findByEmail(String email);
	User createOrUpdate(User u);
	boolean delete(Integer id);

}
