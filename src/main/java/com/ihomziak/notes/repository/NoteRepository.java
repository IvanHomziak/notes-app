package com.ihomziak.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ihomziak.notes.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findByOwnerUsername(String ownerUsername);

}
