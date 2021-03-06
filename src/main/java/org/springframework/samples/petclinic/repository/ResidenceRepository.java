package org.springframework.samples.petclinic.repository;


import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;

/*
 * Copyright 2002-2013 the original author or authors.

import java.util.Collection;

import org.springframework.dao.DataAccessException;

/**
 * Repository class for <code>Owner</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface ResidenceRepository {

	Collection<Residence> findAll() throws DataAccessException;
	
	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Residence findById(int id) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or updating it.
	 * @param owner the <code>Owner</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Residence residence) throws DataAccessException;
	
	Residence findByRequest(Request request) throws DataAccessException;

	Collection<Request> findReqsResidence() throws DataAccessException;
	
	Residence findByEmployee(Employee employee) throws DataAccessException;
	
	Residence findByItem(Item item) throws DataAccessException;
}
