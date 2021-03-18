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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


@Entity
@Table(name="sessions")
public class Session implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
	private Integer id;
	
	@Column(name = "status",columnDefinition = "char(1)")
	private String status;
	
	@Column(name = "token", columnDefinition = "varchar(100)")
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
	
	public Session()
	{
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiresdAt() {
		return expiresdAt;
	}

	public void setExpiresdAt(Date expiresdAt) {
		this.expiresdAt = expiresdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
