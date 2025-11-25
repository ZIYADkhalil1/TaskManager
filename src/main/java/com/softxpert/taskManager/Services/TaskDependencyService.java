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
	

	/**
	 * Adds a dependency relationship for a given task.
	 *
	 * <p>This method checks whether the dependency already exists for the specified task. 
	 * If it does, no changes are made and an appropriate message is returned. Otherwise,
	 * a new {@code TaskDependency} is created and persisted.</p>
	 *
	 * @param taskId        the ID of the task to which the dependency will be added
	 * @param dependencyId  the ID of the task that will become a dependency
	 * @return a message indicating whether the dependency was added or already exists
	 */
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
	
	/**
	 * Updates an existing dependency of a task by replacing it with a new dependency.
	 *
	 * <p>The method performs two validations:
	 * <ul>
	 *   <li>Ensures the new dependency does not already exist for the task.</li>
	 *   <li>Ensures the original dependency exists before attempting to update it.</li>
	 * </ul>
	 * If validations pass, the dependency is updated and saved.</p>
	 *
	 * @param taskId            the ID of the task whose dependency is being updated
	 * @param oldDependencyId   the ID of the existing dependency to be replaced
	 * @param newDependencyId   the ID of the new dependency to assign
	 * @return a message indicating success or describing why the update could not be made
	 */
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
