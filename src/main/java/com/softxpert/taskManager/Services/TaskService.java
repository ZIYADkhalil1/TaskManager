package com.softxpert.taskManager.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softxpert.taskManager.DTOs.RegisterTask;
import com.softxpert.taskManager.DTOs.UpdateTask;
import com.softxpert.taskManager.Entities.Task;
import com.softxpert.taskManager.Repositories.TaskRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public Task getTasks(Long id) {
		Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
		return task;
	}
	
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
	
	public List<Task> getTasksOfAssignee(Integer assignee){
		return taskRepository.findByAssignee(assignee);
	}
	
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	
	public Task updateAssignee(Long id, Integer newAssignee) {
        Task taskWithNewAssignee = getTasks(id);

		taskWithNewAssignee.setAssignee(newAssignee);
		return taskRepository.save(taskWithNewAssignee);
	}
	
	public Task updateTaskStatus(Long id, Integer newStatusId) {
        Task taskWithNewStatus =  getTasks(id);

		taskWithNewStatus.setStatusId(newStatusId);
		return taskRepository.save(taskWithNewStatus);
	}
	public Task updateTask(Long taskId, UpdateTask req) {

	    Task task = getTasks(taskId);
	    objectMapper.updateValue(task, req);

	    return taskRepository.save(task);
	}

}
