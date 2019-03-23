package com.cts.task.service;

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
		validation(task);		
		return taskRepo.saveAndFlush(task);		
	}
	
	@Transactional
	public Task updateTask(Task task) throws RestException
	{		
		validation(task);				 
	 return taskRepo.saveAndFlush(task);
	}
	
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

    private void validation(Task task) throws RestException
    {
    	if (task.getTaskName() == null) {
			String error = " Task name is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.TaskName", error);
			throw new RestException(apiError);
		}else if (task.getPriority() == null) {
			String error = " Priority is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.Priority", error);
			throw new RestException(apiError);
		}else if (task.getStartDate() == null) {
			String error = " Start date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.StartDate", error);
			throw new RestException(apiError);
		} else if (task.getEndDate() == null) {
			String error = " End date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.EndDate", error);
			throw new RestException(apiError);
		}
    }
}
