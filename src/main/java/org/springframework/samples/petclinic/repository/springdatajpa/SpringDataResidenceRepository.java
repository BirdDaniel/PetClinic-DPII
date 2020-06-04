/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.repository.ResidenceRepository;

/**
 * Spring Data JPA specialization of the {@link VetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataResidenceRepository extends ResidenceRepository, Repository<Residence, Integer> {
//	@Query("select r from Residence r WHERE :request in elements(r.requests)")
//	public Residence findByRequest(@Param("request") Request request);
	
	@Query("SELECT resi.requests FROM Residence resi")
	public Collection<Request> findReqsResidence();
	
	@Query("select DISTINCT r from Residence r LEFT JOIN FETCH r.items WHERE :employee in elements(r.employees)")
	public Residence findWithItemsByEmployee(@Param("employee") Employee employee);

	@Query("select r from Residence r WHERE :request in elements(r.requests)")
	public Residence findByRequest(@Param("request") Request request);
	
	@Query("select r from Residence r WHERE :employee in elements(r.employees)")
	public Residence findByEmployee(@Param("employee")Employee employee);
	
	@Query("select r from Residence r WHERE :item in elements(r.items)")
	public Residence findByItem(@Param("item") Item item);
}
