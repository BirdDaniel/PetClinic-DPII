
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.RequestRepository;


public interface SpringDataRequestRepository extends RequestRepository, Repository<Request, Integer> {
	
	@Query("SELECT req FROM Request req WHERE req.id=:id")
	public Request findById(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.employee.id =:id")
	public List<Request> findRequestsByEmployeeId(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.employee.id =:id AND req.status=true")
	public List<Request> findAcceptedByEmployeeId(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.owner.id =:id ORDER BY req.serviceDate DESC")
	public Request findByOwnerId(@Param("id") int id);

	@Query("SELECT req FROM Request req WHERE req.owner.id =:id AND req.status = true")
	public Collection<Request> findAcceptedByOwnerId(@Param("id") int ownerId);
	
	//FIND PETS CURRENTLY IN A RESIDENCE
	@Query("SELECT req,resi FROM Request req,Residence resi WHERE req in elements(resi.requests) AND req.status = true AND req.owner.id = :id  AND CURRENT_TIMESTAMP BETWEEN req.serviceDate AND req.finishDate")
	public Collection<Request> findAcceptedResByOwnerId(@Param("id") int ownerId);

}
