package com.proyect.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.proyect.dto.OutputMessage;
import com.proyect.util.JasperReportUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import javax.servlet.ServletContext;
import javax.sql.DataSource;

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

	private final DataSource dataSource;
	private final ServletContext servletContext;

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;


	@Autowired
	public UserController(ServletContext servletContext,DataSource dataSource)
	{
		this.servletContext=servletContext;
		this.dataSource=dataSource;
	}


	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers()
	{
		var users = uService.getAll();
		
		return ResponseEntity.ok(new ResponseData(users,"OK","v1.0.0"));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody User u,Principal principal)
	{
		var userOp = uService.findByEmail(u.getEmail());
		
		if(userOp.isPresent() && userOp!=null)
		{
			return ResponseEntity.ok(new ResponseData("Correo ya registrado","KO","v1.0.0"));
		}
		else
		{
			var pass = u.getPassword();
			u.setPassword(passwodEncoder.encode(pass));
			u.setStatus("A");
			var user = uService.createOrUpdate(u);

			//simpMessagingTemplate.convertAndSend("/topic/greetings",user);
			OutputMessage out = new OutputMessage(
					"user",
					"hola",
					new SimpleDateFormat("HH:mm").format(new Date()));

			//simpMessagingTemplate.convertAndSendToUser(principal.getName(), "queue/notification", out);
			
			return ResponseEntity.ok(new ResponseData(user,"OK","v1.0.0"));
		}
		
	}

	@PutMapping(value = "update")
	public ResponseEntity<?> updateUser(@RequestBody User u)
	{
		var userOp = uService.findByEmail(u.getEmail());

		if(userOp.isPresent()  && userOp!=null)
		{
			if(userOp.get().getId()==u.getId())
			{
				var user =uService.createOrUpdate(u);

				return  new ResponseEntity(user,HttpStatus.OK);
			}
			else
			{
				return  new ResponseEntity("No se puede cambiar el correo",HttpStatus.CONFLICT);
			}
		}
		else
		{
			return  new ResponseEntity("No se puede cambiar el correo",HttpStatus.CONFLICT);
		}

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
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(Integer id)
	{
		var delete = false;
		
		delete= uService.delete(id);
		
		if(delete)
		{
			return ResponseEntity.ok(new ResponseData("Usuario eliminado satisfactoriamente","OK","v1.0.0"));
		}
		else
		{
			return ResponseEntity.ok(new ResponseData("No se pudo eliminar al usuario","KO","v1.0.0"));
		}
	}

	@GetMapping(value="report-users-to-pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getReporttoPDFUsers() throws JRException, IOException {
		var jaspeReport = new JasperReportUtil();
		var users = uService.getAll();

		return new ResponseEntity<>(jaspeReport.createPdfReport(users,"Diego Rodriguez"), HttpStatus.OK);
	}

	@GetMapping(value = "users-status")
	public ResponseEntity<?> getUsersByStatus(@RequestParam(name = "status", required = true) String status)
	{
		var statusFormat = status.trim();

		if(statusFormat.length()>1)
		{
			return  new ResponseEntity("error: length from variable is > 1", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else
		{
			var users = uService.findByStatusUser(statusFormat);
			return  new ResponseEntity(users, HttpStatus.OK);
		}
	}


	@MessageMapping("/secured/room")
	public void sendSpecific(
			@Payload String msgTo,
			Principal user,
			@Header("simpSessionId") String sessionId) throws Exception {

		OutputMessage out = new OutputMessage(
				"user",
				"hola",
				new SimpleDateFormat("HH:mm").format(new Date()));

		simpMessagingTemplate.convertAndSendToUser(
				msgTo, "/secured/user/queue/specific-user", out);
	}



}
