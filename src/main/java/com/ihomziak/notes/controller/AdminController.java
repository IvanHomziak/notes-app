package com.ihomziak.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ihomziak.notes.dto.UserDTO;
import com.ihomziak.notes.models.Role;
import com.ihomziak.notes.models.User;
import com.ihomziak.notes.repository.RoleRepository;
import com.ihomziak.notes.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private RoleRepository roleRepository;

	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getusers")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
	}

	@PutMapping("/update-role")
	public ResponseEntity<String> updateUserRole(@RequestParam Long userId,
		@RequestParam String roleName) {
		userServiceImpl.updateUserRole(userId, roleName);
		return ResponseEntity.ok("User role updated");
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		return new ResponseEntity<>(userServiceImpl.getUserById(id), HttpStatus.OK);
	}

	@PutMapping("/update-lock-status")
	public ResponseEntity<String> updateAccountLockStatus(@RequestParam Long userId, @RequestParam boolean lock) {
		userServiceImpl.updateAccountLockStatus(userId, lock);
		return ResponseEntity.ok("Account lock status updated");
	}

	@GetMapping("/roles")
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@PutMapping("/update-expiry-status")
	public ResponseEntity<String> updateAccountExpiryStatus(@RequestParam Long userId, @RequestParam boolean expire) {
		userServiceImpl.updateAccountExpiryStatus(userId, expire);
		return ResponseEntity.ok("Account expiry status updated");
	}

	@PutMapping("/update-enabled-status")
	public ResponseEntity<String> updateAccountEnabledStatus(@RequestParam Long userId, @RequestParam boolean enabled) {
		userServiceImpl.updateAccountEnabledStatus(userId, enabled);
		return ResponseEntity.ok("Account enabled status updated");
	}

	@PutMapping("/update-credentials-expiry-status")
	public ResponseEntity<String> updateCredentialsExpiryStatus(@RequestParam Long userId, @RequestParam boolean expire) {
		userServiceImpl.updateCredentialsExpiryStatus(userId, expire);
		return ResponseEntity.ok("Credentials expiry status updated");
	}

	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestParam Long userId, @RequestParam String password) {
		try {
			userServiceImpl.updatePassword(userId, password);
			return ResponseEntity.ok("Password updated");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
