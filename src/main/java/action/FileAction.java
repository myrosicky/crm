package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import service.FileService;
import service.iface.UploadOpsService;
import vo.UploadDataVo;

import com.google.common.base.Optional;
import com.opensymphony.xwork2.ActionSupport;

@InterceptorRefs(value = { 
		@InterceptorRef(
				value = "fileUpload"
				, params={
						"allowType", "image/png,image/gif,image/jpeg,application/octet-stream"
						,"maximumSize", "7000000"
						} 
				) 
		,@InterceptorRef(value = "params") 
		,@InterceptorRef(value = "chain") 
		,@InterceptorRef(value = "basicStack") 
	}
)
@Results(value = { 
		@Result(
			    name = "download", 
			    type = "stream", 
			    params = { 
			        "contentType", "application/octet-stream", 
			        "inputName", "stream", //the name of the variable,not expression format like ${...]
			        "bufferSize", "1024", 
			        "contentDisposition", "attachment;filename=\"${filename}\"",
			        "allowCaching","false"
			    }
			)
		,@Result(
			    name = "upload"
			    ,type = "chain"
			    ,params = { 
				        "actionName", "${actionName}",
				        "method","${method}"
				 }
			)
		,@Result(
			    name = "fail"
			    ,location="/WEB-INF/webpages/loginError.jsp"
			)
		,@Result(
			    name = "success"
			    ,location="/WEB-INF/webpages/uploadAttachment.jsp"
			)
		}
)
public class FileAction extends ActionSupport {
	final static Logger LOGGER = LoggerFactory.getLogger(FileAction.class);

	private FileService fileService;
	
	@Autowired
	private BeanFactory beanFactory;

	private  Long fileId;
	private  File uploadFile;
	private  String contentType;
	private  String filename;
	private  String filePath;
	private  InputStream stream;
	private String serviceName;
	private String redirectLocation;
	private String parser;
	private String type;
	
	
	@Autowired
	public void setFileService(){
		fileService = (FileService) beanFactory.getBean(this.parser+"FileService");
	}

	public String execute(){
		return SUCCESS;
	}
	
	@Transactional
	public String upload(){
		Map<String,String> tempData = fileService.resolve(this.uploadFile, this.filename);
		UploadOpsService uploadOpsService = (UploadOpsService) beanFactory.getBean(serviceName);
		UploadDataVo uploadDataVo = new UploadDataVo();
		uploadDataVo.setTempData(tempData);
		uploadDataVo.setType(this.type);
		uploadOpsService.doUploadBusinessLogic(uploadDataVo);
		return "upload";
	}
	
	
	public String download(){
		Optional<File> file = fileService.getFile(fileId);
		if(!file.isPresent()) return "fail";
		try {
			stream = new FileInputStream(file.get());
			this.filename = file.get().getName();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getLocalizedMessage());
		    return "fail";
		}
		return "download";
	}

	public void setUpload(File file) {
        this.uploadFile = file;
     }

     public void setUploadContentType(String contentType) {
        this.contentType = contentType;
     }

     public void setUploadFileName(String filename) {
        this.filename = filename;
     }
}
