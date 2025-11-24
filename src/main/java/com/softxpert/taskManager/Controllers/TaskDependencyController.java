package com.softxpert.taskManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softxpert.taskManager.Services.TaskDependencyService;

@RestController
@RequestMapping("/taskDependency")
@PreAuthorize("hasAuthority('ROLE_1')")
public class TaskDependencyController {

	@Autowired
	private TaskDependencyService taskDependencyService;
	
	@PostMapping("/add")
	public String addDependency(@RequestParam Long taskId, @RequestParam Long dependencyId) {
		return taskDependencyService.addDependency(taskId, dependencyId);	
	}
	
	
	@PutMapping("/edit")
	public String editDependency(@RequestParam Long taskId, @RequestParam Long oldDependencyId, @RequestParam Long newDependencyId) {
		return taskDependencyService.editDependency(taskId, oldDependencyId, newDependencyId);
	}
}
