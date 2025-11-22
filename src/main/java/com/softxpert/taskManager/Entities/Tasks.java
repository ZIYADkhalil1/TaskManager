package com.softxpert.taskManager.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tasks")
public class Tasks {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
