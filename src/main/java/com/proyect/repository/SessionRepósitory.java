package com.proyect.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyect.entity.Session;

@Repository
public interface SessionRepósitory extends JpaRepository<Session, Integer> {
	
	Optional<Session> findByToken(String token);
	
	@Query("SELECT s FROM Session s WHERE s.user.id=:userId ORDER BY s.id DESC LIMIT 1")
	Optional<Session> findLastSessionByUserId(Integer userId);

}
