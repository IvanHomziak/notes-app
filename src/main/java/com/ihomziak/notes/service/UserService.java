package com.ihomziak.notes.service;

import java.util.List;

import com.ihomziak.notes.dto.UserDTO;
import com.ihomziak.notes.models.User;

public interface UserService {
	void updateUserRole(Long userId, String roleName);

	List<User> getAllUsers();

	UserDTO getUserById(Long id);

	User findByUsername(String username);
}
