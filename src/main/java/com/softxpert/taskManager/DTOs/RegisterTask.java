package com.softxpert.taskManager.DTOs;

import java.time.LocalDate;

public record RegisterTask(
		Long id,
		String title,
		String description,
		LocalDate dueDate,
		Integer assignee,
		Integer statusId
)
{}
