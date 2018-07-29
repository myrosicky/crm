package po;

import java.io.InputStream;

import com.google.api.client.util.Strings;

public class FileVo {

	private String type;
	private String filename;
	private InputStream stream;
	
	public String getType() {
		return Strings.isNullOrEmpty(type)?"image/jpeg":type ;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	
	
	
}
