package service.rest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class MyRestTemplate {
	
	private final static Logger log = LoggerFactory.getLogger(MyRestTemplate.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private @Value("${ws.url}") String url;
	private @Value("${ws.username}") String username;
	private @Value("${ws.password}") String password;
	
	public <T> T getForObject(Class<T> clazz)  {
		return restTemplate.getForObject(url + clazz.getSimpleName() + ".obj?username={1}&password={2}" , clazz, username, password);
	}
	
	public <T> T getForObject(String actionName, Class<T> clazz, Object... urlNameAndVariables)  {
		String targetUrl = generateUrl(actionName, clazz, urlNameAndVariables);
		return restTemplate.getForObject( targetUrl, clazz, username, password, urlNameAndVariables);
	}
	
	public <T> T postForObject(String actionName, Class<T> clazz, Object... urlNameAndVariables)  {
		String targetUrl = generateUrl(actionName, clazz, urlNameAndVariables);
		return restTemplate.postForObject( targetUrl, null, clazz, username, password, urlNameAndVariables);
	}
	
	private <T> String generateUrl(String actionName, Class<T> clazz, Object... urlNameAndVariables){
		int length = urlNameAndVariables.length;
		Assert.isTrue(Integer.lowestOneBit(length)==1, "urlNameAndVariables length must be even number");
		StringBuilder targetUrl = new StringBuilder(url);
		targetUrl.append(actionName).append(".do?username={1}&password={2}");
		for(int i=0;i<length; i+=2){
			targetUrl
				.append(urlNameAndVariables[i])
				.append("={").append(i+3).append("}")
				;
			urlNameAndVariables[(i+1)>>1] = urlNameAndVariables[i+1];
		}
		urlNameAndVariables = Arrays.copyOf(urlNameAndVariables, length>>1);
		log.debug("targetUrl.toString():"+targetUrl.toString());
		log.debug("urlNameAndVariables:"+urlNameAndVariables);
		return targetUrl.toString();
	}
	
}
