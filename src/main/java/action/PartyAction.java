package action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import po.Party;
import repository.iface.FileDao;
import service.PartyOpsService;

@Results(value = { 
		@Result(
			    name = "download"
			    ,type = "chain"
			    ,params = { 
				        "actionName", "fileAction",
				        "method","download"
				 }
			)
		}
)
@Action
public class PartyAction extends ActionSupport {

	@Autowired
	private PartyOpsService partyOpsService;
	
	@Autowired
	private FileDao fileDao;
	
	
	public String saveParty() {
		
		Party party= new Party();
		partyOpsService.saveParty(party);
		return "";
	}
	
	public String downloadPartyForm() {
		ActionContext.getContext().getValueStack().set("fileId", fileDao.findIdByName("PartyInfoForm.xlsx"));
		return "download";
	}

}
