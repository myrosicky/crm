package service.usr;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import po.CrmUser;
import service.iface.UserOpsService;
import utils.CipherUtil;
import utils.NamingUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

@Service
public class UserOpsServiceImpl implements UserOpsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> template;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOps;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> zsetOps;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valOps;

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsername(java.lang.String)
	 */
	@Override
	public CrmUser findByUsername(String username) {
		String sk_userName = String.format(NamingUtil.sk_userName_format, username);
		String sk_user = valOps.get(sk_userName);
		return sk_user==null? null : new ObjectMapper().convertValue(hashOps.entries(sk_user),CrmUser.class);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(po.CrmUser)
	 */
	@Override
	public void saveUser(CrmUser user) {
		String sk_user = String.format(NamingUtil.sk_user_format, user.getId());
		if (zsetOps.rank(NamingUtil.sk_users, sk_user) == null) {
			zsetOps.add(NamingUtil.sk_users, sk_user, 0);
		}
		valOps.set(user.getUsername(), sk_user);
		hashOps.putAll(sk_user, new ObjectMapper().convertValue(user, Map.class));
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsernameContaining(java.lang.String)
	 */
	@Override
	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term) {
		Set<String> keys = template.keys("%"+term+"%");
		CopyOnWriteArrayList<CrmUser> users = null;
		if (keys!=null) {
			users = new CopyOnWriteArrayList<CrmUser>();
			for (String username : keys) {
				users.add(findByUsername(username));
			}
		}
		return users;
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#checkUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<CrmUser> checkUser(String username, String password) {
		CrmUser user = findByUsername(username);
		return (Optional<CrmUser>) (!CipherUtil.MD5Match(password, user.getPassword())?Optional.absent():Optional.of(user));
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveUser(String username, String password, String remoteHost) {
		CrmUser user = new CrmUser();
		user.setUsername(username);
		user.setPassword(CipherUtil.MD5(password));
		user.setGeneral_ip(remoteHost);
		saveUser(user);
	}

}
