package com.ihomziak.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.ihomziak.notes.dto.UserDTO;
import com.ihomziak.notes.models.AppRole;
import com.ihomziak.notes.models.Role;
import com.ihomziak.notes.models.User;
import com.ihomziak.notes.repository.RoleRepository;
import com.ihomziak.notes.repository.UserRepository;
import com.ihomziak.notes.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void updateUserRole(Long userId, String roleName) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		AppRole appRole = AppRole.valueOf(roleName);
		Role role = roleRepository.findByRoleName(appRole)
			.orElseThrow(() -> new RuntimeException("Role not found"));
		user.setRole(role);
		userRepository.save(user);
	}


	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public UserDTO getUserById(Long id) {
		//        return userRepository.findById(id).orElseThrow();
		User user = userRepository.findById(id).orElseThrow();
		return convertToDto(user);
	}

	private UserDTO convertToDto(User user) {
		return new UserDTO(
			user.getUserId(),
			user.getUserName(),
			user.getEmail(),
			user.isAccountNonLocked(),
			user.isAccountNonExpired(),
			user.isCredentialsNonExpired(),
			user.isEnabled(),
			user.getCredentialsExpiryDate(),
			user.getAccountExpiryDate(),
			user.getTwoFactorSecret(),
			user.isTwoFactorEnabled(),
			user.getSignUpMethod(),
			user.getRole(),
			user.getCreatedDate(),
			user.getUpdatedDate()
		);
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUserName(username);
		return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
	}
}
