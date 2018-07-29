package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import po.Party;
import repository.PartyRepository;
import service.iface.UploadOpsService;
import vo.UploadDataVo;

@Service
public class PartyOpsService implements UploadOpsService {

	@Autowired
	private PartyRepository partyRepository;
	
	@Autowired
	private FileService fileService;
	
	
	public List<Party> findByManagerId(long managerId) {
		return partyRepository.findByManagerId(managerId);
	}

	public void saveParty(Party party) {
		partyRepository.save(party);
	}
	
	@Override
	public void doUploadBusinessLogic(UploadDataVo uploadDataVo) {
		Party party = new ObjectMapper().convertValue(uploadDataVo.getTempData(), Party.class);
		partyRepository.save(party);
	}
}
