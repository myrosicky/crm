package context;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:config.properties")
public class DataSourceConfig {


	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env
				.getProperty("ds.driverClassName"));
		basicDataSource.setUrl(env.getProperty("ds.url"));
		basicDataSource.setUsername(env.getProperty("ds.DBusername"));
		basicDataSource.setPassword(env.getProperty("ds.password"));
		return basicDataSource;
	}
}
