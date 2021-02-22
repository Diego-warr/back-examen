package com.proyect.controller;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.entity.Session;
import com.proyect.entity.User;
import com.proyect.response.ResponseData;
import com.proyect.response.ResponseLogin;
import com.proyect.service.SessionService;
import com.proyect.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rest.v1/user", produces = "application/hal+json")
public class UserController {
	
	@Autowired
	private SessionService sService;
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private PasswordEncoder passwodEncoder;
	
	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers()
	{
		var users = uService.getAll();
		
		return ResponseEntity.ok(new ResponseData(users,"OK","v1.0.0"));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody User u)
	{
		var pass = u.getPassword();
		u.setPassword(passwodEncoder.encode(pass));
		u.setStatus("A");
		var user = uService.createOrUpdate(u);
		
		return ResponseEntity.ok(new ResponseData(user,"OK","v1.0.0"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam(name="email", required=true) String email,@RequestParam(name="password", required=true) String password)
	{
		var userOp = uService.findByEmail(email);
		
		System.out.println(password);
		
		if(userOp.isPresent() && userOp!=null)
		{
			var user = userOp.get();
			
			System.out.println(user.getPassword());
			if(passwodEncoder.matches(password, user.getPassword()))
			{
				var calendar = Calendar.getInstance();
				var session = new Session();
				session.setUser(user);
				session.setStatus("A");
				session.setToken(UUID.randomUUID().toString());
				session.setCreatedAt(calendar.getTime());
				var responseSession = sService.createOrUpdate(session);
				var response =new ResponseLogin(user,responseSession);
				
				return ResponseEntity.ok(new ResponseData(response, "OK", "v1.0.0"));
			}
			else
			{
				return ResponseEntity.ok(new ResponseData("Error validación", "OK", "v1.0.0"));
			}
		}
		else
		{
			return ResponseEntity.ok(new ResponseData("Usuario no registrado", "OK", "v1.0.0"));
		}
	}
	
	@GetMapping("/session")
	public ResponseEntity<?> getSession(@RequestParam(name="token", required=true) String token)
	{
		var sessionOp = sService.findByToken(token);
		
		if(sessionOp.isPresent() && sessionOp!=null)
		{
			var session = sessionOp.get();
			
			return ResponseEntity.ok(new ResponseData(session,"OK","v1.0.0"));
		}
		else
		{
			return ResponseEntity.ok(new ResponseData("ERROR","KO","v1.0.0"));
		}
		
	}
	
	@PutMapping("/logout")
	public ResponseEntity<?> logOut(@RequestParam(name="token", required=true) String token)
	{
		var session = sService.deleteSession(token);
		
		if(session!=null)
		{
			return ResponseEntity.ok(new ResponseData("Logout","OK","v1.0.0"));
		}
		else
		{
			return ResponseEntity.ok(new ResponseData("Error","KO","v1.0.0"));
		}
		
	}

}
