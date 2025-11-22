package com.softxpert.taskManager.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TaskStatus {
	
	@Id
	private Long id;
	private String status;
}
