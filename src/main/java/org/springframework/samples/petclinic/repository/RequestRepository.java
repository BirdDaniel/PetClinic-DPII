package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Request;

public interface RequestRepository {

	Request findByOwnerId(int id);

	List<Request> findRequestsByEmployeeId(int id);

	Request findById(int id);

	void save(Request request) throws DataAccessException;

	Collection<Request> findAcceptedAll();

}
