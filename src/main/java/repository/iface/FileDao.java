package repository.iface;

import java.io.File;
import java.util.Map;

public interface FileDao {

	public File findIdByName(String name);

	public void saveTempData(Map<String, String> data);

	public File findById(Long fileId);

	public void save(File uploadFile, String fileName);

	public void save(File file);

}
