package com.cts.task.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.task.dao.TaskRepository;
import com.cts.task.exception.RestException;
import com.cts.task.model.Task;


@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class TaskServiceTest {

	private static final LocalDate  START_DATE=(LocalDate.parse("2018-11-23",
            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	private static final LocalDate  END_DATE=(LocalDate.parse("2018-11-24",
            DateTimeFormatter.ofPattern("yyyy-MM-dd")));
@Mock
private TaskRepository TaskRepositoryMock;


@InjectMocks
private TaskServiceImpl taskService;

@Test
public void addTask() throws RestException
{		
	Task sourceTask = new Task();
	sourceTask.setTaskId(new Long(22));
	sourceTask.setTaskName("Task1");
	sourceTask.setPriority(new Long(1));
	sourceTask.setStartDate(START_DATE);
	sourceTask.setEndDate(END_DATE);
	sourceTask.setStatus(0);
	Mockito.when(TaskRepositoryMock.saveAndFlush(Mockito.any())).thenReturn(sourceTask);
	Task  returned = taskService.addTask(sourceTask);	   	
	assertEquals(sourceTask, returned);	
	
}
@Test
public void updateTask() throws RestException
{		
	Task sourceTask = new Task();
	sourceTask.setTaskId(new Long(22));
	sourceTask.setTaskName("Task1");
	sourceTask.setPriority(new Long(1));
	sourceTask.setStartDate(START_DATE);
	sourceTask.setEndDate(END_DATE);
	sourceTask.setStatus(0);
	Mockito.when(TaskRepositoryMock.saveAndFlush(Mockito.any())).thenReturn(sourceTask);
	Task  returned = taskService.updateTask(sourceTask);	   	
	assertEquals(sourceTask, returned);	
	
}

@Test
public void deleteTask() {
	Long taskId=new Long(12);
	
	taskService.deleteTask(taskId);
    System.out.println("inside test cases delete");    
 // verify the mocks   
    verify(TaskRepositoryMock, times(1)).deleteById(eq(taskId));
  
}

@Test
public void getTaskById() throws RestException
{		
	Task sourceTask = new Task();
	sourceTask.setTaskId(new Long(22));
	sourceTask.setTaskName("Task1");
	sourceTask.setPriority(new Long(1));
	sourceTask.setStartDate(START_DATE);
	sourceTask.setEndDate(END_DATE);
	sourceTask.setStatus(0);
	 	
	Optional<Task> checkNull =  
            Optional.ofNullable(sourceTask);    
    Mockito.when(TaskRepositoryMock.findById(any(Long.class))).thenReturn(checkNull);    
    Task  returned = taskService.getTaskById(new Long(22));
    System.out.println("inside test cases"+returned);
    assertEquals(sourceTask, returned);	
}
@Test
public void listTasks() throws RestException
{		
	Task sourceTask = new Task();
	sourceTask.setTaskId(new Long(22));
	sourceTask.setTaskName("Task1");
	sourceTask.setPriority(new Long(1));
	sourceTask.setStartDate(START_DATE);
	sourceTask.setEndDate(END_DATE);
	sourceTask.setStatus(0);
	 	List<Task> tasks= new ArrayList<Task>();
	 	tasks.add(sourceTask);
	   
    Mockito.when(TaskRepositoryMock.findAll()).thenReturn(tasks);    
    List<Task>  returned = taskService.listTasks();
    System.out.println("inside test cases"+returned);
    assertEquals(tasks, returned);	
}
}