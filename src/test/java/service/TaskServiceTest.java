package service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import po.Dictionary;
import service.iface.TaskService;
import context.WebMvcContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebMvcContext.class,context.DataSourceConfig.class,context.DispatcherServletInitializer.class,context.JpaContext.class,context.RedisContext.class})
public class TaskServiceTest {

	@Autowired
	TaskService taskService;
	
	@Test
	public void testReceiveTasks() {
//		System.err.println(taskService.receiveTasks("1", 0, 10).get(0).getContent());
		System.err.println("EMAIL:"+ Dictionary.Category.EMAIL.name()+":"+Dictionary.Category.EMAIL.value()); 
		
	}

	@Test
	public void testSaveTask() {
//		Task newTask = new Task();
//		newTask.setCategory("1");
//		newTask.setContent("22");
//		taskService.saveTask(newTask);
	}

	@Test
	public void testRemoveTask() {
//		Task newTask = new Task();
//		newTask.setCategory("1");
//		newTask.setContent("22");
//		taskService.removeTask(newTask);
	}

	
	
}
