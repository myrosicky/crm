package controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.base.Optional;

import po.CrmUser;
import po.Dictionary;
import po.Party;
import po.Task;
import service.PartyOpsService;
import service.SessionOpsService;
import service.iface.TaskService;
import service.iface.UserOpsService;
import utils.DictionaryService;

@Controller
public class MyController {

	private final Logger logger = LoggerFactory.getLogger(MyController.class);

	final String LOGIN_PAGE = "login";

	@Autowired
	private UserOpsService userOpsService;

	@Autowired
	private PartyOpsService partyOpsService;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private SessionOpsService sessionUtil;
	
	@Autowired
	DictionaryService dictionaryUtil;

	@RequestMapping("/login.do")
	@ResponseBody
	Map<String, Object> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse resp) {
		ModelAndView rtnModel = new ModelAndView();
		Map<String, Object> model = rtnModel.getModel();
		StringBuffer errMsg = new StringBuffer();
		boolean success = true;
		
		Optional<CrmUser> user = userOpsService.checkUser(username,password);
		if (!user.isPresent())  errMsg.append("用户名或密码不正确"); 
		success = errMsg.length() == 0;
		
		if (success) 
			resp.addCookie(sessionUtil.genCookie(user.get()));
		
		model.put("errMsg", errMsg.toString());
		model.put("success", success);
		return model;
	}

	@RequestMapping("/register.do")
	@ResponseBody
	Map<String, Object> register(
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String password,
			HttpServletRequest request) {
		ModelAndView rtnModel = new ModelAndView();
		Map<String, Object> model = rtnModel.getModel();
		StringBuffer errMsg = new StringBuffer();

		CrmUser user = userOpsService.findByUsername(username);
		if (user != null) {
			errMsg.append("用户名已存在");
		} else {
			try {
				userOpsService.saveUser(username,password,request.getRemoteHost());
			} catch (Exception e) {
				errMsg.append("register fail");
				logger.error(e.getMessage());
			}
		}
		model.put("errMsg", errMsg.toString());
		model.put("success", errMsg.length() == 0);
		return model;
	}

	@RequestMapping(value = "/{view}.htm")
	ModelAndView gotoView(@PathVariable("view") String view,
			HttpServletRequest req, HttpServletResponse response) {
		try {
//			if (!LOGIN_PAGE.equals(view))
//				sessionUtil.getUserID(req.getCookies());
			return new ModelAndView(view);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			RedirectView redirectView = new RedirectView("/" + LOGIN_PAGE + ".htm");
			redirectView.setContextRelative(true);
			redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
			return new ModelAndView(redirectView);
		}

	}

	@RequestMapping("/receivePopwords.do")
	@ResponseBody
	List<String> receivePopwords(@RequestParam String term) {
		try {
			List<String> list = new ArrayList<String>();
			List<CrmUser> users = userOpsService.findByUsernameContaining(term);
			for (CrmUser crmUser : users) {
				list.add(crmUser.getUsername());
			}
			return list;
		} catch (Exception e) {
			return Collections.<String> emptyList();
		}
	}

	@RequestMapping("/receiveParties.do")
	@ResponseBody
	List<Party> receiveParties(HttpServletRequest req,
			@RequestParam(required = false) Boolean missed) {
		try {
			if (missed != null && missed) {
				return Collections.<Party> emptyList();
			}
			long managerId = sessionUtil.getUserID(req.getCookies());
			return partyOpsService.findByManagerId(managerId);
		} catch (Exception e) {
			return Collections.<Party> emptyList();
		}
	}

	@RequestMapping("/savePartyInfo.do")
	@ResponseBody
	void savePartyInfo(HttpServletRequest req,
			@RequestParam Map<String, String> params) throws Exception {
		try {
			long managerId = sessionUtil.getUserID(req.getCookies());
			CrmUser manager = new CrmUser();
			manager.setId(managerId);
			Party party = new Party();
			party.setManager(manager);
			party.setName(params.get("name"));
			partyOpsService.saveParty(party);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@RequestMapping("/receiveTasks.do")
	@ResponseBody
	List<Task> receiveTasks(
			@RequestParam(required = false) String taskStatus,
			@RequestParam(required = false, defaultValue = "0") Integer fromIndex,
			@RequestParam(required = false, defaultValue = "15") Integer toIndex) throws Exception {
		try {
			return taskService.receiveTasks(
					Dictionary.Status.valueOf(taskStatus.toUpperCase()), fromIndex, toIndex);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@RequestMapping("/saveTask.do")
	@ResponseBody
	void saveTask(@RequestParam Map<String, String> params, HttpServletRequest req) {
		try {
			Task task = new Task(Dictionary.Category.valueOf(params.get("category")
					.toUpperCase()), params.get("content"), DateFormat
					.getInstance().parse(params.get("deadLine")),
					sessionUtil.getUserID(req.getCookies()));
			taskService.saveTask(task);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping("/recieveChance.do")
	Model recieveChance(Model model, HttpSession session){
		
		return model;
	}
	
	
//	@RequestMapping("/loadDict.do")
//	@ResponseBody
//	List<DictionaryItem> loadDict(@RequestParam String dictName, @RequestParam(required=false) String dictVal) {
//		try {
//			return dictionaryUtil.loadDict(dictName,dictVal);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}finally{
//			return null;
//		}
//	}
	
	
	
}
