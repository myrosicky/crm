package controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import context.WebMvcContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebMvcContext.class,context.DataSourceConfig.class,context.DispatcherServletInitializer.class,context.JpaContext.class,context.RedisContext.class})
public class MyControllerTest {

	@Autowired
	private MyController myController;
	
	@Test
	public void testLogin() {
//		MockClientHttpRequest request = new MockClientHttpRequest() ;
//		MockClientHttpResponse resp = new MockClientHttpResponse();
//		myController.login("a", "a", request, resp);
	}

	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	public void testGotoView() {
		fail("Not yet implemented");
	}

	@Test
	public void testReceivePopwords() {
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveParties() {
		fail("Not yet implemented");
	}

	@Test
	public void testSavePartyInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveTasks() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveTask() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecieveChance() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadDict() {
		fail("Not yet implemented");
	}

}
