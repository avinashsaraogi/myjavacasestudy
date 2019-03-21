package com.cts.task.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.task.dao.TaskRepository;
import com.cts.task.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
@Autowired	private TaskRepository taskRepo;
	
	@Transactional	
		
	public Task addTask(Task task) 
	{		return taskRepo.save(task);	}
	
	public Task updateTask(Task task) 
	{		return taskRepo.save(task);	}
	
	@Transactional	
		
	public void deleteTask(Long taskId) 
	{		taskRepo.deleteById(taskId);	}
	
		
	public List<Task> listTasks() 
	{		return taskRepo.findAll();	}	
	
	
	public Task getTaskById(Long taskId) 
	{		
		Optional<Task> optTask = taskRepo.findById(taskId);		
		Task task = optTask.isPresent()?optTask.get():null;		
	    return task;	
	}


}
