package com.ihomziak.notes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihomziak.notes.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);

	Boolean existsByEmail(String email);

	Boolean existsByUserName(String username);
}

