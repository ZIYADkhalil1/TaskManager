package com.softxpert.taskManager.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tasks {

	@Id
	private Long id;
	private String title;
	private String description;
	@Column(name = "due_date")
	private java.time.LocalDateTime dueDate;
	private Integer assignee;
	@Column(name = "created_at")
	private java.time.LocalDateTime createdAt;
	@Column(name = "status_id")
	private Integer statusId;
}
