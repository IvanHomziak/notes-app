package com.ihomziak.notes.service;

import java.util.List;
import java.util.Optional;

import com.ihomziak.notes.dto.UserDTO;
import com.ihomziak.notes.models.User;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public interface UserService {
	void updateUserRole(Long userId, String roleName);

	List<User> getAllUsers();

	UserDTO getUserById(Long id);

	User findByUsername(String username);

	void updatePassword(Long userId, String password);

	void updateAccountLockStatus(Long userId, boolean lock);

	void updateAccountExpiryStatus(Long userId, boolean expire);

	void updateAccountEnabledStatus(Long userId, boolean enabled);

	void updateCredentialsExpiryStatus(Long userId, boolean expire);

	void generatePasswordResetToken(String email);

	void resetPassword(String token, String newPassword);

	Optional<User> findByEmail(String email);

	User registerUser(User newUser);

	GoogleAuthenticatorKey generate2faSecretKey(Long userId);

	boolean validate2faCode(Long userId, int code);

	void enable2fa(Long userId);

	void disable2fa(Long userId);
}
