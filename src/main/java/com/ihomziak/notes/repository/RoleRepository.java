package com.ihomziak.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.ihomziak.notes.models.AppRole;
import com.ihomziak.notes.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(AppRole appRole);
}
