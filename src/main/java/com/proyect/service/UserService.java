package com.proyect.service;

import java.util.List;
import java.util.Optional;

import com.proyect.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.proyect.entity.User;

@Service
public interface UserService {
	
	public List<User> getAll();
	Optional<User> findByEmail(String email);
	List<UserDTO> findByStatusUser(String status);
	User createOrUpdate(User u);
	boolean delete(Integer id);

}
