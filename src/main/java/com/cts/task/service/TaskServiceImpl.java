package com.cts.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.task.dao.TaskRepository;
import com.cts.task.exception.ApiError;
import com.cts.task.exception.RestException;
import com.cts.task.model.Task;

@Service("taskService")
public class TaskServiceImpl implements  TaskService{
@Autowired	private TaskRepository taskRepo;
	

	@Transactional		
	public Task addTask(Task task) throws RestException
	{			
		if (task.getParentTask().getTaskId() == 0){
			task.setParentTask(null);
		}
		return taskRepo.saveAndFlush(task);		
	}
	
	@Transactional
	public Task updateTask(Task task) throws RestException
	{
		if (task.getParentTask().getTaskId() == 0){
			task.setParentTask(null);
		}
		return taskRepo.saveAndFlush(task);
	}
	
	@Transactional			
	public void deleteTask(Long taskId) 
	{		taskRepo.deleteById(taskId);	}
	
		
	public List<Task> listTasks() 
	{
		List<Task> taskList = taskRepo.findAll();
		
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getParentTask() == null){
				Task parentTask = new Task();
				parentTask.setTaskId(new Long(0));
				parentTask.setTaskName("--No Parent--");
				taskList.get(i).setParentTask(parentTask);
			}
		}
		
		return taskList;	
		
	}	
	
	
	public Task getTaskById(Long taskId) 
	{		
		Optional<Task> optTask = taskRepo.findById(taskId);		
		Task task = optTask.isPresent()?optTask.get():null;		
	    return task;	
	}

    
}
