package po;

import java.util.Collections;

public class Dictionary {
	public enum Status {
		ONGOING("进行中")
		, COMPLETED("已完成")
		, HANDOVER("交给同事")
		;
		private String value;
		public String value(){return this.value;};
		Status(String comment){
			this.value = comment;
		}
	}
	public enum Category {
		EMAIL("邮件")
		,PHONE("电话");
		private String value;
		public String value(){return this.value;};
		Category(String comment){
			this.value = comment;
		}
	}
}
