package com.ihomziak.notes.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomziak.notes.models.AuditLog;
import com.ihomziak.notes.models.Note;
import com.ihomziak.notes.repository.AuditLogRepository;
import com.ihomziak.notes.service.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

	private final AuditLogRepository auditLogRepository;

	@Autowired
	public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}

	@Override
	public void logNoteCreation(String userName, Note note) {
		// Implementation for logging note creation
		AuditLog log = new AuditLog();
		log.setAction("CREATE");
		log.setUserName(userName);
		log.setNoteId(note.getId());
		log.setNoteContent(note.getContent());
		log.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(log);
	}

	@Override
	public void logNoteUpdate(String userName, Note note) {
		// Implementation for logging note creation
		AuditLog log = new AuditLog();
		log.setAction("UPDATE");
		log.setUserName(userName);
		log.setNoteId(note.getId());
		log.setNoteContent(note.getContent());
		log.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(log);
	}

	@Override
	public void logNoteDeletion(String userName, Long note) {
		// Implementation for logging note creation
		AuditLog log = new AuditLog();
		log.setAction("DELETE");
		log.setUserName(userName);
		log.setNoteId(note);
		log.setTimestamp(LocalDateTime.now());
		auditLogRepository.save(log);
	}

	@Override
	public List<AuditLog> getAllAuditLogs() {
		return auditLogRepository.findAll();
	}

	@Override
	public List<AuditLog> getLogsForNoteId(final Long id) {
		return auditLogRepository.findByNoteId(id);
	}
}
