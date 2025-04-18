package com.ihomziak.notes.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ihomziak.notes.models.AppRole;
import com.ihomziak.notes.models.Role;
import com.ihomziak.notes.models.User;
import com.ihomziak.notes.repository.RoleRepository;
import com.ihomziak.notes.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true,
	jsr250Enabled = true
)
public class SecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests)
			-> requests
			.requestMatchers("/api/admin/**").hasRole("ADMIN")
			.requestMatchers("/public/**").permitAll()
			.anyRequest().authenticated()
		);
		http.csrf(AbstractHttpConfigurer::disable);
		//http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(
		RoleRepository roleRepository,
		UserRepository userRepository,
		PasswordEncoder passwordEncoder) {
		return args -> {
			Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
				.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

			Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
				.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

			if (!userRepository.existsByUserName("user1")) {
				User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
				user1.setAccountNonLocked(false);
				user1.setAccountNonExpired(true);
				user1.setCredentialsNonExpired(true);
				user1.setEnabled(true);
				user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
				user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
				user1.setTwoFactorEnabled(false);
				user1.setSignUpMethod("email");
				user1.setRole(userRole);
				userRepository.save(user1);
			}

			if (!userRepository.existsByUserName("admin")) {
				User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
				admin.setAccountNonLocked(true);
				admin.setAccountNonExpired(true);
				admin.setCredentialsNonExpired(true);
				admin.setEnabled(true);
				admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
				admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
				admin.setTwoFactorEnabled(false);
				admin.setSignUpMethod("email");
				admin.setRole(adminRole);
				userRepository.save(admin);
			}
		};
	}
}

