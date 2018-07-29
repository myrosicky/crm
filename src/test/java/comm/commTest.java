package comm;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import context.WebMvcContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebMvcContext.class,context.DataSourceConfig.class,context.DispatcherServletInitializer.class,context.JpaContext.class,context.RedisContext.class})
public class commTest {

}
