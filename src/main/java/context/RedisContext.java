package context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@Configuration
public class RedisContext {

	private @Value("${redis.host}") String hostName;
	private @Value("${redis.port}") String port;
	private @Value("${redis.password}") String password;
	private @Value("${redis.salt}") String salt;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		// JedisConnectionFactory jedisConnectionFactory = new
		// JedisConnectionFactory(redisSentinelConfiguration());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setHostName(hostName);
		jedisConnectionFactory.setPort(Integer.parseInt(port));
		jedisConnectionFactory.setPassword(new Md5PasswordEncoder()
				.encodePassword(password, salt));
		return jedisConnectionFactory;
	}

	// @Bean
	public RedisSentinelConfiguration redisSentinelConfiguration() {
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
				.master("mymaster").sentinel("127.0.0.1", 26379)
				.sentinel("127.0.0.1", 26380);
		return sentinelConfig;
	}

	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		// redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		return cacheManager;
	}
	
	public static void main(String[] args) {
		
	System.err.println(new Md5PasswordEncoder()
				.encodePassword("lL0106140153", "myrosicky@163.com"));
	}
}
