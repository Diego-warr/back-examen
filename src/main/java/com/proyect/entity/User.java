package com.proyect.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
public class User implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
	private Integer id;
	
	@Column(name = "first_name", updatable = true, columnDefinition = "varchar(100)")
	private String firstName;
	
	@Column(name = "last_name", updatable = true, columnDefinition = "varchar(100)")
	private String lastName;
	
	@Column(name = "email", updatable = false, columnDefinition = "varchar(100)")
	private String email;
	
	@Column(name = "password", updatable = true, columnDefinition = "TEXT")
	private String password;
	
	@Column(name = "status", columnDefinition = "char(1)")
	private String status;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
	@JsonIgnore
	private List<Session> session;
	
	public User()
	{
		
	}

	public User(Integer id, String firstName, String lastName, String email, String password, String status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.status = status;
	}
}
