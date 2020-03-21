
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.RequestRepository;


public interface SpringDataRequestRepository extends RequestRepository, Repository<Request, Integer> {

	@Override
	@Query("SELECT request FROM Request request WHERE request.status = true")
	public Collection<Request> findAcceptedAll();
	
	@Query("SELECT req FROM Request req WHERE req.owner =:id")
	public Request findByOwnerId(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.employee=:id")
	public List<Request> findRequestsByEmployeeId(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.id=:id")
	public Request findById(@Param("id") int id);

}
