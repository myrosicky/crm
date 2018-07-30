package repository.iface;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import po.Party;

public interface PartyRepository extends CrudRepository<Party, Long>  {

	List<Party> findByManagerId(long managerId);
	
}
