package com.ihomziak.notes.service.impl;

import com.ihomziak.notes.models.Note;
import com.ihomziak.notes.repository.NoteRepository;
import com.ihomziak.notes.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

	private NoteRepository noteRepository;
	private AuditLogServiceImpl auditLogService;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository, AuditLogServiceImpl auditLogService) {
		this.noteRepository = noteRepository;
		this.auditLogService = auditLogService;
	}

	@Override
	public Note createNoteForUser(String username, String content) {
		Note note = new Note();
		note.setContent(content);
		note.setOwnerUsername(username);
		Note savedNote = noteRepository.save(note);
		auditLogService.logNoteCreation(username, savedNote);
		return savedNote;
	}

	@Override
	public Note updateNoteForUser(Long noteId, String content, String username) {
		Note note = noteRepository.findById(noteId).orElseThrow(()
			-> new RuntimeException("Note not found"));
		note.setContent(content);
		Note updatedNote = noteRepository.save(note);
		auditLogService.logNoteUpdate(username, updatedNote);
		return updatedNote;
	}

	@Override
	public void deleteNoteForUser(Long noteId, String username) {
		Note note = noteRepository.findById(noteId).orElseThrow(()
			-> new RuntimeException("Note not found"));
		auditLogService.logNoteDeletion(username, note.getId());
		noteRepository.delete(note);
	}

	@Override
	public List<Note> getNotesForUser(String username) {
		List<Note> personalNotes = noteRepository
			.findByOwnerUsername(username);
		return personalNotes;
	}
}


