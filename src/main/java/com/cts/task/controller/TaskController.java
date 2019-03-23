package com.cts.task.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.task.exception.ApiError;
import com.cts.task.exception.RestException;
import com.cts.task.model.Task;
import com.cts.task.service.TaskService;



@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
		
	@PostMapping("/task")
	public ResponseEntity<?> addTask(@RequestBody Task task) throws RestException{
		task =taskService.addTask(task);
		return new ResponseEntity<Task>
		(task,HttpStatus.OK);
	}
	@PutMapping("/task/{id}")
	public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable("id") Long taskId) throws RestException{

		ResponseEntity<Task> response = null;
		 
		if(taskService.getTaskById(taskId) == null)
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else{
			task.setTaskId(taskId);
			task =taskService.updateTask(task);
			response = new ResponseEntity<>(task,HttpStatus.OK);
		}
		return response;
	}
	@GetMapping("/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId) throws RestException {
                
        ResponseEntity<Task> response = null;		
        Task task = taskService.getTaskById(taskId);		
		if(task == null)
		{			
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Exception.notFound", "");
		throw new RestException(apiError);
		}
		else
			response = new ResponseEntity<>(task,HttpStatus.OK);		
		return response;
    }
	@GetMapping("/task")
	public ResponseEntity<List<Task>> getAllTasks(){
		return new ResponseEntity<List<Task>>(taskService.listTasks(),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable("id") Long taskId) throws RestException{
		ResponseEntity<Void> response = null;		
		Task task = taskService.getTaskById(taskId);		
		if(task == null)
		{
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Exception.notFound", "");
		throw new RestException(apiError);
		}
		else{
			taskService.deleteTask(taskId);
			response = new ResponseEntity<>(HttpStatus.OK);
		}		
		return response;
		
	}
	
}
