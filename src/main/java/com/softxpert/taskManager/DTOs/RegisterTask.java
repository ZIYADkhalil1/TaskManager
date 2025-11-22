package com.softxpert.taskManager.DTOs;

public record RegisterTask(
		Long id,
		String title,
		String description,
		java.time.LocalDateTime dueDate,
		Integer assignee,
		Integer statusId
)
{}
