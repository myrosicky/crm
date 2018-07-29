package action;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Results(value = { 
		@Result(name="success",location="/WEB-INF/webpages/ajax/alter.jsp")
		}
)
public class ProfileAction extends ActionSupport {
	
	final static Logger LOGGER = LoggerFactory.getLogger(ProfileAction.class);
	
	private String content;
	private String author;
	
	@RequiredStringValidator(message="no content are you ok")
	public String getContent() {
		return content;
	}

	@RequiredStringValidator(message="no author are you ok?")
	public String getAuthor() {
		return author;
	}

	public void setContent(String content) {
			this.content = content;
	}


	public void setAuthor(String author) {
			this.author = author;
	}

	public String execute() {
		return SUCCESS;
	}
	
	
}
