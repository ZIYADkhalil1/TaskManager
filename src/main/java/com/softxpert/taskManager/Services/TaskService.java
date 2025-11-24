package com.softxpert.taskManager.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.jpa.domain.Specification;

import com.softxpert.taskManager.DTOs.RegisterTask;
import com.softxpert.taskManager.DTOs.TaskWithDependencies;
import com.softxpert.taskManager.DTOs.UpdateTask;
import com.softxpert.taskManager.Entities.Task;
import com.softxpert.taskManager.Entities.TaskStatus;
import com.softxpert.taskManager.Repositories.TaskDependencyRepository;
import com.softxpert.taskManager.Repositories.TaskRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskDependencyRepository taskDependencyRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Retrieves a task by its ID.
	 *
	 * @param id the task ID
	 * @return the task
	 * @throws RuntimeException if the task is not found
	 */
	public Task getTasks(Long id) {
		Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
		return task;
	}
	
	/**
	 * Adds a new task using the provided registration DTO.
	 *
	 * @param newTask the task creation request
	 * @return a confirmation message
	 */
	public String addTask(RegisterTask newTask) {
		
		Task task = new Task();
        task.setTitle(newTask.title());
        task.setDescription(newTask.description());
        if (newTask.assignee() != null) {
        task.setAssignee(newTask.assignee());        	
        }
        task.setDueDate(newTask.dueDate());
        task.setStatusId(newTask.statusId());
        taskRepository.save(task);
        
        return("Task Added Succesfully");

	}
	
	/**
	 * Retrieves all tasks assigned to a specific user, including dependency IDs.
	 *
	 * @param assignee the user ID of the assignee
	 * @return a list of tasks with their dependencies
	 */
	public List<TaskWithDependencies> getTasksOfAssignee(Integer assignee){
		return taskRepository.findByAssignee(assignee).stream()
	            .map(task -> new TaskWithDependencies(
	                    task,
	                    taskDependencyRepository.findDependencyIdByTaskId(task.getId())
	            ))
	            .toList();
	}
	
	/**
	 * Retrieves tasks that match the given filters.
	 *
	 * @param assignee filter by assignee (nullable)
	 * @param status filter by task status (nullable)
	 * @param dueFrom filter by due date lower bound (nullable)
	 * @param dueTo filter by due date upper bound (nullable)
	 * @return a list of matching tasks with their dependencies
	 */
	public List<TaskWithDependencies> getFilteredTasks(
	        Integer assignee,
	        TaskStatus status,
	        LocalDateTime dueFrom,
	        LocalDateTime dueTo
	) {

		Specification<Task> spec = (root, query, cb) -> cb.conjunction();


	    if (assignee != null) {
	        spec = spec.and((root, query, cb) ->
	                cb.equal(root.get("assignee"), assignee));
	    }

	    if (status != null) {
	        spec = spec.and((root, query, cb) ->
	                cb.equal(root.get("status"), status));
	    }

	    if (dueFrom != null) {
	        spec = spec.and((root, query, cb) ->
	                cb.greaterThanOrEqualTo(root.get("dueDate"), dueFrom));
	    }

	    if (dueTo != null) {
	        spec = spec.and((root, query, cb) ->
	                cb.lessThanOrEqualTo(root.get("dueDate"), dueTo));
	    }

	    return taskRepository.findAll(spec).stream()
	            .map(task -> new TaskWithDependencies(
	                    task,
	                    taskDependencyRepository.findDependencyIdByTaskId(task.getId())
	            ))
	            .toList();
	}


	/**
	 * Updates only the assignee of a specific task.
	 *
	 * @param id the task ID
	 * @param newAssignee the new assignee ID
	 * @return the updated task
	 */	
	public Task updateAssignee(Long id, Integer newAssignee) {
        Task taskWithNewAssignee = getTasks(id);

		taskWithNewAssignee.setAssignee(newAssignee);
		return taskRepository.save(taskWithNewAssignee);
	}
	
	/**
	 * Updates the status of a task, validating dependency completion when needed.
	 *
	 * @param id the task ID
	 * @param newStatusId the new status value
	 * @return the updated task
	 * @throws ResponseStatusException if dependencies are incomplete
	 */
	public Task updateTaskStatus(Long id, Integer newStatusId) {

	    Task taskWithNewStatus = getTasks(id);

	    if (newStatusId.equals(1)) {
	        List<Long> dependencyIds = taskDependencyRepository.findDependencyIdByTaskId(id);

	        if (!dependencyIds.isEmpty()) {

	        	List<Task> dependencies = taskRepository.findAllById(dependencyIds);
	            List<Task> incompleteDependencies = dependencies.stream()
	                .filter(t -> t.getStatusId() != 1)
	                .toList();
	            if (!incompleteDependencies.isEmpty()) {
	                String message = incompleteDependencies.stream()
	                    .map(t -> "Dependency task " + t.getId() + " is not completed")
	                    .collect(Collectors.joining(", "));

	                throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "Cannot update task. " + message
	                );
	            }
	        }
	    }
	    taskWithNewStatus.setStatusId(newStatusId);
	    return taskRepository.save(taskWithNewStatus);
	}

	/**
	 * Updates a task using an UpdateTask DTO (partial update).
	 *
	 * @param taskId the id of the task to update
	 * @param req the update request
	 * @return the updated task
	 */
	public Task updateTask(Long taskId, UpdateTask req) {

	    Task task = getTasks(taskId);
	    objectMapper.updateValue(task, req);

	    return taskRepository.save(task);
	}

}
