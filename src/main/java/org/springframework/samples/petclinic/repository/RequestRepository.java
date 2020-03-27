package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Request;

public interface RequestRepository {

	Request findByOwnerId(int id);

	List<Request> findRequestsByEmployeeId(int id);

	Request findById(int id);

	void save(Request request) throws DataAccessException;

	Collection<Request> findAcceptedAll();
	
	Collection<Request> findAcceptedByOwnerId(int ownerId);
	
	Collection<Request> findAcceptedResByOwnerId(int ownerId) throws DataAccessException;

}
