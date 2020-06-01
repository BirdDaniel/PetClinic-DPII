package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Request;

public interface RequestRepository {

	List<Request> findRequestsByEmployeeId(int id);

	Request findById(int id);

	void save(Request request) throws DataAccessException;

	Collection<Request> findAcceptedByOwnerId(int ownerId);

	Collection<Request> findAcceptedByEmployeeId(int employeeId);

	Collection<Request> findAcceptedResByOwnerId(int ownerId) throws DataAccessException;

}
