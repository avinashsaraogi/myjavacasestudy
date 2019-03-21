package com.cts.task.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.task.exception.ApiError;
import com.cts.task.exception.RestException;
import com.cts.task.model.Task;
import com.cts.task.service.TaskService;


@CrossOrigin
@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
		
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public ResponseEntity<?> addTask(@RequestBody Task task) throws RestException{
		
		System.out.println("task.getStatus()"+task.getStatus());
		if (task.getStartDate() == null) {
			String error = " Start date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.StartDate", error);
			throw new RestException(apiError);
		} else if (task.getEndDate() == null) {
			String error = " End date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.EndDate", error);
			throw new RestException(apiError);
		}else if (task.getStatus()<0 || task.getStatus()>1) {
			String error = "Status needs to be updated";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Check.task.Status", error);
			throw new RestException(apiError);
		}
		return new ResponseEntity<Task>
		(taskService.addTask(task),HttpStatus.OK);
	}
	@PutMapping("/task/{id}")
	public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable("id") Long taskId) throws RestException{

		ResponseEntity<Task> response = null;
		if (task.getStartDate() == null) {
			String error = " Start date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.StartDate", error);
			throw new RestException(apiError);
		} else if (task.getEndDate() == null) {
			String error = " End date is missing";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "NotNull.task.EndDate", error);
			throw new RestException(apiError);
		}else if (task.getStatus()<0 || task.getStatus()>1) {
			String error = "Status needs to be updated";
			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Check.task.Status", error);
			throw new RestException(apiError);
		}
		if(taskService.getTaskById(taskId) == null)
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else{
			task.setTaskId(taskId);
			taskService.updateTask(task);
			response = new ResponseEntity<>(task,HttpStatus.OK);
		}
		return response;
	}
	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId) throws RestException {
        System.out.println("Fetching Task with id {}" + taskId);
        
        ResponseEntity<Task> response = null;
		
        Task task = taskService.getTaskById(taskId);
		
		if(task == null)
		{
			//System.out.println("task is empty");
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Exception.notFound", "");
		throw new RestException(apiError);
		}
		else
			response = new ResponseEntity<>(task,HttpStatus.OK);
		
		return response;
    }
	@CrossOrigin
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
	/*
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)	
	public ApiError handleException(final Throwable exc) {
		System.out.println("Throwable error for test case "+ exc.getMessage());
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, exc.getLocalizedMessage(), exc.getMessage());
		
		return apiError;
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage("Exception.unexpected", null, locale);
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/
}
