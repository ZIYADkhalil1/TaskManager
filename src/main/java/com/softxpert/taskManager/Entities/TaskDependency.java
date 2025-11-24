package com.softxpert.taskManager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "task_dependencies")
public class TaskDependency {
	@Column(name = "task_id")
	private Long taskId;
	@Column(name = "dependency_id")
	private Long dependencyId;
}
