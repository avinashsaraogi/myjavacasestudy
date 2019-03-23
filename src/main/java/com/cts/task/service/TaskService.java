package com.cts.task.service;

import java.util.List;

import com.cts.task.exception.RestException;
import com.cts.task.model.Task;

public interface TaskService {
	
	public Task addTask(Task task) throws RestException;
	public Task updateTask(Task task) throws RestException;
	public void deleteTask(Long taskId);
	public List<Task> listTasks();
	public Task getTaskById(Long taskId);
	
}
