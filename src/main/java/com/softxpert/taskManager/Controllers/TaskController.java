package com.softxpert.taskManager.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softxpert.taskManager.DTOs.RegisterTask;
import com.softxpert.taskManager.Services.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_1')")
public class TaskController {

	private final TaskService taskService;
	@PostMapping("/add")
	public ResponseEntity<?> addTask(@RequestBody RegisterTask newTask){
		try {
			String message = taskService.addTask(newTask);
			return ResponseEntity.ok(message);
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
}
