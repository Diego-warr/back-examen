package com.proyect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
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
