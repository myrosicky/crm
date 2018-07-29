package repository;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Repository;

import repository.iface.FileDao;

@Repository
public class FileDaoImpl implements FileDao {

	@Override
	public File findIdByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTempData(Map<String, String> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public File findById(Long fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(File uploadFile, String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(File file) {
		// TODO Auto-generated method stub
		
	}

}
