package com.softxpert.taskManager.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTask {
	private String title;
	private String description;
	private java.time.LocalDateTime dueDate;
	private Integer assignee;
	private java.time.LocalDateTime createdAt;
	private Integer statusId;
}
