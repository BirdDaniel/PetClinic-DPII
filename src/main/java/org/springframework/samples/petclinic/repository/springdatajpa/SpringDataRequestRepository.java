package org.springframework.samples.petclinic.repository.springdatajpa;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.RequestRepository;


public interface SpringDataRequestRepository extends RequestRepository, Repository<Request, Integer> {

	
	@Override
	@Query("SELECT req FROM Request req WHERE req.owner =:id")
	public Request findByOwnerId(@Param("id") int id);

}
