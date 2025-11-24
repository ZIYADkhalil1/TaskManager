package com.softxpert.taskManager.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.softxpert.taskManager.DTOs.RegisterTask;
import com.softxpert.taskManager.DTOs.TaskWithDependencies;
import com.softxpert.taskManager.DTOs.UpdateTask;
import com.softxpert.taskManager.Entities.Task;
import com.softxpert.taskManager.Entities.TaskStatus;
import com.softxpert.taskManager.Entities.User;
import com.softxpert.taskManager.Services.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;
	
	@PreAuthorize("hasAuthority('ROLE_1')")
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
	
	@PreAuthorize("hasAuthority('ROLE_1')")
	@PutMapping("/updateAssignee/{taskId}/{assignee}")
	public Task updateTaskAssignee(@PathVariable Long taskId, @PathVariable Integer assignee) {
		return taskService.updateAssignee(taskId, assignee);
	}
	
	@PreAuthorize("hasAuthority('ROLE_1')")
	@GetMapping()
	public List<TaskWithDependencies> getFilteredTasks(
	        @RequestParam(required = false) Integer assignee,
	        @RequestParam(required = false) TaskStatus status,
	        @RequestParam(required = false)
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueFrom,
	        @RequestParam(required = false)
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueTo
	) {
	    return taskService.getFilteredTasks(assignee, status, dueFrom, dueTo);
	}
	
	@GetMapping("/{id}")
	public List<TaskWithDependencies> getTasksOfAssignee(
	        @PathVariable Integer id,
	        @AuthenticationPrincipal User user
	) {
	    boolean isAdmin = user.getRole() == 1;

	    if (!isAdmin && !user.getId().equals(id)) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
	    }

	    return taskService.getTasksOfAssignee(id);
	}
	
	@PutMapping("/updateTaskStatus/{taskId}/{status}")
	public Task updateTaskStatus(
			@PathVariable Long taskId,
			@PathVariable Integer status,
			@AuthenticationPrincipal User user) {
		Task task =  taskService.getTasks(taskId);
		Integer taskAssignee = task.getAssignee();
		boolean isAdmin = user.getRole() == 1;
	    if (!isAdmin && !user.getId().equals(taskAssignee)) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
	    }
		
		return taskService.updateTaskStatus(taskId, status);
	}
	
	@PreAuthorize("hasAuthority('ROLE_1')")
	@PutMapping("/updateTask/{taskId}")
	public Task updateTask(@RequestParam Long taskId, @RequestBody UpdateTask task) {
		return taskService.updateTask(taskId, task);
	}
}
