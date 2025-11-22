package com.softxpert.taskManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softxpert.taskManager.DTOs.RegisterTask;
import com.softxpert.taskManager.Entities.Task;
import com.softxpert.taskManager.Repositories.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
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
}
