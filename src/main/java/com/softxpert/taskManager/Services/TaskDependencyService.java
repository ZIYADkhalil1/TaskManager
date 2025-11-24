package com.softxpert.taskManager.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softxpert.taskManager.Entities.TaskDependency;
import com.softxpert.taskManager.Repositories.TaskDependencyRepository;

@Service
public class TaskDependencyService {

	@Autowired
	private TaskDependencyRepository taskDependencyRepository;
	

	
	public String addDependency(Long taskId, Long dependencyId) {
	   
		Optional<TaskDependency> existing =
	            taskDependencyRepository.findByTaskIdAndDependencyId(taskId, dependencyId);

	    if (existing.isPresent()) {
	        return "Dependency already exists";
	    }
	    
	    TaskDependency newDependency = new TaskDependency();
	    newDependency.setTaskId(taskId);
	    newDependency.setDependencyId(dependencyId);

	    taskDependencyRepository.save(newDependency);

	    return "Dependency added successfully";
		
	}
	
	
	
	public String editDependency(Long taskId, Long oldDependencyId, Long newDependencyId) {

	    Optional<TaskDependency> checkDuplicate =
	            taskDependencyRepository.findByTaskIdAndDependencyId(taskId, newDependencyId);

	    if (checkDuplicate.isPresent()) {
	        return "Dependency already exists";
	    }

	    Optional<TaskDependency> existingDependency =
	            taskDependencyRepository.findByTaskIdAndDependencyId(taskId, oldDependencyId);

	    if (existingDependency.isEmpty()) {
	        return "Original dependency not found";
	    }

	    TaskDependency dependency = existingDependency.get();
	    dependency.setDependencyId(newDependencyId);

	    taskDependencyRepository.save(dependency);

	    return "Dependency saved successfully";
	}

}
