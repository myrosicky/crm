package vo;

import java.util.Map;

public class UploadDataVo {
	private Map<String,String> tempData;
	private String type;
	
	public Map<String, String> getTempData() {
		return tempData;
	}
	public void setTempData(Map<String, String> tempData) {
		this.tempData = tempData;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
