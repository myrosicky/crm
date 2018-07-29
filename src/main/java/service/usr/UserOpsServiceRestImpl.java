package service.usr;

import java.util.concurrent.CopyOnWriteArrayList;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import po.CrmUser;
import service.iface.UserOpsService;
import service.rest.MyRestTemplate;

import com.google.common.base.Optional;

//@Service("userOpsServiceRestImpl")
public class UserOpsServiceRestImpl implements UserOpsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserOpsService userOpsService;
	
	@Autowired
	private MyRestTemplate restTemplate;
	
	@Autowired(required=false)
	public UserOpsServiceRestImpl() {
		super();
		userOpsService = restTemplate.getForObject(UserOpsService.class);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsername(java.lang.String)
	 */
	@Override
	public CrmUser findByUsername(String username) {
		return userOpsService.findByUsername(username);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(po.CrmUser)
	 */
	@Override
	public void saveUser(CrmUser user) {
		userOpsService.saveUser(user);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsernameContaining(java.lang.String)
	 */
	@Override
	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term) {
		return userOpsService.findByUsernameContaining(term);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#checkUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<CrmUser> checkUser(String username, String password) {
		return userOpsService.checkUser(username,password);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveUser(String username, String password, String remoteHost) {
		userOpsService.saveUser(username, password, remoteHost);
	}

}
