package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.util.ValueStack;

import repository.iface.FileDao;

@Service
public abstract class FileService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FileService.class) ;
	
	@Autowired
	private FileDao fileDao;
	
	public Optional<File> getFile(Long fileId){
		return Optional.of(fileDao.findById(fileId));
	}
	
	public void record(File uploadFile, String fileName){
		fileDao.save(uploadFile,fileName);
	}
	
	public Map<String,String> resolve(File uploadFile, String fileName){
		record(uploadFile,fileName);
		return parse(uploadFile);
	}
	
	public void delete(File uploadFile){
		try {
			Files.delete( FileSystems.getDefault().getPath(uploadFile.getAbsolutePath()));
		} catch (IOException e) {
			uploadFile.deleteOnExit();
		}
	}
	
	public abstract Map<String,String> parse(File uploadFile);
	
}
