package com.cts.task.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TASK")
public class Task {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@RestResource
	private Long taskId;		
	
	@NotNull(message = "Task name cannot be empty")
	@Column(unique=true,nullable=false,length = 100)
	private String taskName;
	
	@NotNull(message = "Priority is missing")
	private Long priority;
	
	@JsonFormat(pattern = "yyyy-MM-dd")	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Start date is missing")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private LocalDate endDate;
	
	private int status;
	
	@ManyToOne(fetch=FetchType.EAGER, optional = true )
	@JoinColumn(name="parent_id", nullable = true)	
	private Task parentTask;
	
	
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName  
				+ " , priority=" + priority + ", status=" + status + "]";
	}
}
