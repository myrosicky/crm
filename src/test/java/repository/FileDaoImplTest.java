package repository;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import repository.iface.FileDao;
import context.WebMvcContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebMvcContext.class,context.DataSourceConfig.class,context.DispatcherServletInitializer.class,context.JpaContext.class,context.RedisContext.class})
public class FileDaoImplTest {

	@Autowired
	private FileDao fileDao;
	
	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fileDao.save(new File(""));
	}

}
