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
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

/**
 * Spring Data JPA specialization of the {@link PetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataPetRepository extends PetRepository, Repository<Pet, Integer> {

	@Override
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	List<PetType> findPetTypes() throws DataAccessException;

	@Modifying
	@Query("DELETE FROM Pet pet WHERE pet.id=:id")
	public void deletePet(@Param("id") int id);
	
	@Override
	@Query("SELECT pet FROM Pet pet WHERE pet.name = :name AND pet.owner.id = :id")
	Collection<Pet> findPetsOfOwnerByName(@Param("id") int ownerId, @Param("name") String name);
	@Query("SELECT pet FROM Pet pet, Request req,Residence resi, Employee emp WHERE req in elements(resi.requests) AND req.pet=pet AND emp=:employee AND req.status = true  AND CURRENT_TIMESTAMP BETWEEN req.serviceDate AND req.finishDate AND emp in elements(resi.employees) ")
	
	public Collection<Pet> findPetResByEmployeeId(@Param("employee") Employee employee);
	//@Query("SELECT pet FROM Pet pet, Request req,Residence resi, Employee emp WHERE req in elements(resi.requests) AND pet in elements (req.pets=pet) AND emp=:employee AND req.status = true  AND CURRENT_TIMESTAMP BETWEEN req.serviceDate AND req.finishDate AND emp in elements(resi.employees) ")
}
