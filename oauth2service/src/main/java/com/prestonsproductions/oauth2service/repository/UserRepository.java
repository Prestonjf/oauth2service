package com.prestonsproductions.oauth2service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestonsproductions.oauth2service.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}
