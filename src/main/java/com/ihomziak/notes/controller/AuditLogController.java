package com.ihomziak.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihomziak.notes.models.AuditLog;
import com.ihomziak.notes.service.AuditLogService;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

	private final AuditLogService auditLogServiceImpl;

	@Autowired
	public AuditLogController(AuditLogService auditLogServiceImpl) {
		this.auditLogServiceImpl = auditLogServiceImpl;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<AuditLog> getAuditLogs() {
		return auditLogServiceImpl.getAllAuditLogs();
	}

	@GetMapping("/note/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<AuditLog> getNoteAuditLogs(@PathVariable Long id) {
		return auditLogServiceImpl.getLogsForNoteId(id);
	}

}
