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
import com.ihomziak.notes.models.User;
import com.ihomziak.notes.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	@Autowired
	UserService userService;

	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getusers")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PutMapping("/update-role")
	public ResponseEntity<String> updateUserRole(@RequestParam Long userId,
		@RequestParam String roleName) {
		userService.updateUserRole(userId, roleName);
		return ResponseEntity.ok("User role updated");
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}


}
