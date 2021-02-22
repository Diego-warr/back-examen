package com.proyect.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="sessions")
public class Session implements Serializable {

	@Id
    @GeneratedValue
	private Integer id;
	
	@Column(name = "status",columnDefinition = "char(1)")
	private String status;
	
	@Column(name = "token", columnDefinition = "vachar(max)")
	private String token;
	
	@Column(name = "created_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private Date createdAt;
	
	@Column(name = "expires_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
	private Date expiresdAt;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
}
