package com.cts.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.task.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
}
