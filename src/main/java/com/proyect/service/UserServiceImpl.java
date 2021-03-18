package com.proyect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.proyect.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.proyect.entity.User;
import com.proyect.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public List<UserDTO> findByStatusUser(String status) {

		List<UserDTO> userDTOList = new ArrayList<>();

		return repo.findByStatusUser(status).stream().map(u-> new UserDTO(Integer.valueOf(String.valueOf(u[0])),String.valueOf(u[1]),String.valueOf(u[2]),String.valueOf(u[3]),String.valueOf(u[4]))).collect(Collectors.toList());
	}

	@Override
	public User createOrUpdate(User u) {
		u.setStatus("A");
		return repo.save(u);
	}

	@Override
	public boolean delete(Integer id) {
		
		var userOp = repo.findById(id);
		var user = new User();
		
		
		if(userOp.isPresent() && userOp!=null)
		{
			user= userOp.get();
			user.setStatus("I");
			repo.save(user);
			
			return true;
		}
		else
		{
			return false;
		}
	}


}
