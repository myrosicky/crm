package service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.base.Optional;

import po.CrmUser;
import utils.CipherUtil;
import utils.NamingUtil;

@Service
public class SessionOpsService {

	private final Logger looger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> template;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOps;
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valOps;
	
	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;
	
	private final String salt = "llsession";
	
	public Cookie genCookie(CrmUser user) {
		Long cookieId = getOnlineUsersNum();
		String sk_user = NamingUtil.genStoreKey(NamingUtil.sk_user_format,user.getId());
		valOps.set(NamingUtil.genStoreKey(NamingUtil.sk_sessionUser_format,String.valueOf(cookieId)), sk_user);
		setOps.add(NamingUtil.sk_onlineUsers, sk_user);
		return new Cookie("ticket3", CipherUtil.MD5(user.getId()+"", salt));
	}

	public long getUserID(Cookie[] cookies) {
		Optional<String> cookieId = getCookieId(cookies);
		looger.debug("cookieid:"+cookieId);
		String sk_user = valOps.get(NamingUtil.genStoreKey(NamingUtil.sk_sessionUser_format,cookieId));
		looger.debug("sk_user:"+sk_user);
		long userID = Long.parseLong((String)hashOps.get(sk_user, "id"));
		looger.debug("userID:"+userID);
		
		Assert.notNull(userID, "not login");
		return userID;
	}

	public void validate(Cookie[] cookies) {
		final long userID_inMemory = getUserID(cookies);
		for (Cookie cookie : cookies) {
			if ("ticket3".equals(cookie.getName())) {
				Assert.isTrue(CipherUtil.MD5Match(userID_inMemory+"", salt, cookie.getValue()),"身份证认证有误，怀疑用户私自篡改数据");
				break;
			}
		}
	}

	public void invalidate(Cookie[] cookies) {
		template.delete(String.format(NamingUtil.sk_sessionUser_format,String.valueOf(getCookieId(cookies))));
		setOps.remove(NamingUtil.sk_onlineUsers, NamingUtil.genStoreKey(NamingUtil.sk_user_format, getUserID(cookies)));
	}

	public Long getOnlineUsersNum() {
		return setOps.size(NamingUtil.sk_onlineUsers);
	}
	
	
	private  Optional<String> getCookieId(Cookie[] cookies) {
		int index = Arrays.binarySearch(cookies, new Cookie(NamingUtil.cookieId, ""), new Comparator<Cookie>() {
			public int compare(Cookie o1, Cookie o2) {
				return o1.getName().compareTo(o2.getName()) ;
			}
		});
		if (index>-1) 
			return Optional.of(cookies[index].getValue());
		return Optional.absent();
	}
}
