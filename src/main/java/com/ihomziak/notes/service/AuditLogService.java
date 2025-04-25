package com.ihomziak.notes.service;

import java.util.List;

import com.ihomziak.notes.models.AuditLog;
import com.ihomziak.notes.models.Note;

public interface AuditLogService {

	void logNoteCreation(String userName, Note note);

	void logNoteUpdate(String userName, Note note);

	void logNoteDeletion(String userName, Long note);

	List<AuditLog> getAllAuditLogs();

	List<AuditLog> getLogsForNoteId(Long id);
}
