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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {

	@Id
    @GeneratedValue
	private Integer id;
	
	@Column(name = "first_name", updatable = false, columnDefinition = "varchar(100)")
	private String firstName;
	
	@Column(name = "last_name", updatable = false, columnDefinition = "varchar(100)")
	private String lastName;
	
	@Column(name = "email", updatable = false, columnDefinition = "varchar(100)")
	private String email;
	
	@Column(name = "password", updatable = false, columnDefinition = "TEXT")
	private String password;
	
	@Column(name = "status", columnDefinition = "char(1)")
	private String status;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
	@JsonIgnore
	private List<Session> session;
	
}
