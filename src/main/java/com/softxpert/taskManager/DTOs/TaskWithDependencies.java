package com.softxpert.taskManager.DTOs;

import java.util.List;

import com.softxpert.taskManager.Entities.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskWithDependencies {
	private Task task;
	private List<Long> dependencies;
}
