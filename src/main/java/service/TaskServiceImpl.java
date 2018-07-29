package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import po.Dictionary.Status;
import po.Task;
import service.iface.TaskService;
import service.rest.MyRestTemplate;

@Service
public class TaskServiceImpl implements TaskService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MyRestTemplate restTemplate;
	
	@Override
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex) {
		return restTemplate.getForObject("receiveTasks", List.class, "status", status, "fromIndex", fromIndex, "toIndex", toIndex);
	}

	@Override
	public void saveTask(Task newTask) {
		restTemplate.postForObject("saveTask", null, null, "newTask", newTask);
	}

	@Override
	public void removeTask(Task task) {
		restTemplate.postForObject("removeTask", null, null, "task", task);
	}/*

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private RedisTemplate<String, String> template;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOps;

	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> zsetOps;

	private final String key_onGoingTask = "ogTask",
			key_completeTask = "compTask", manager = "managerTask%1$x",
			key_tasks = "tasks", key_taskPattern = "task%1$x";

	private final int maxNumPerReq = 20;

	TaskServiceImpl() {

	}

	 (non-Javadoc)
	 * @see service.task.TaskService#receiveTasks(service.task.TaskServiceImpl.Status, java.lang.Integer, java.lang.Integer)
	 
	@Override
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex) {
		Assert.isTrue(fromIndex >= 0 && toIndex >= 0, "范围不合法");
		Assert.isTrue(toIndex - fromIndex + 1 <= maxNumPerReq, "请求记录总数超出限定数！");

		List<Task> rtnList = new CopyOnWriteArrayList<Task>();
		Set<String> keys = 
				status == Status.ONGOING ? zsetOps.range(key_onGoingTask, fromIndex, toIndex) : 
				status == Status.COMPLETED ? zsetOps.range(key_completeTask, fromIndex, toIndex) : 
				status == Status.HANDOVER ? zsetOps.range(key_completeTask, fromIndex, toIndex) : 
								null;
		if (keys != null) {
			for (String key : keys) {
				rtnList.add(new ObjectMapper().convertValue(hashOps.entries(key), Task.class));
			}
		}
		return rtnList;
	}

	 (non-Javadoc)
	 * @see service.task.TaskService#saveTask(po.Task)
	 
	@Override
	@SuppressWarnings("unchecked")
	public void saveTask(Task newTask) {
			String key_task = "", key_manager = String.format(manager,
					newTask.getManagerID());
			logger.info("newTask.getId():" + newTask.getId());
			logger.info("newTask.getDeadLine():" + newTask.getDeadLine());
			if (newTask.getId() == 0) {
				newTask.setId(setOps.size(key_tasks));
				key_task = String.format(key_taskPattern, newTask.getId());
				setOps.add(key_tasks, key_task);
				setOps.add(key_manager, key_task);
				zsetOps.add(key_onGoingTask, key_task,
						zsetOps.size(key_onGoingTask));
			} else if (newTask.getDeadLine().before(new Date())) {
				key_task = String.format(key_taskPattern, newTask.getId());
				zsetOps.remove(key_onGoingTask, key_task);
				zsetOps.add(key_completeTask, key_task,
						zsetOps.size(key_onGoingTask));
			}
			hashOps.putAll(key_task,
					new ObjectMapper().convertValue(newTask, Map.class));
	}

	 (non-Javadoc)
	 * @see service.task.TaskService#removeTask(po.Task)
	 
	@Override
	public void removeTask(Task task) {
		String key_task = String.format(key_taskPattern, task.getId());
		setOps.remove(key_tasks, key_task);
		zsetOps.remove(key_onGoingTask, key_task);
		zsetOps.remove(key_completeTask, key_task);
		template.delete(key_task);
	}

*/}
