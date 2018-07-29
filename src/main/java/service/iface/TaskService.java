package service.iface;

import java.util.List;


import po.Dictionary.Status;
import po.Task;

public interface TaskService {
	
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex);

	public void saveTask(Task newTask);

	public void removeTask(Task task);

}