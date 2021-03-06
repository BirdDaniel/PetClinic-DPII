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
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.EmployeeRepository;

public interface SpringDataEmployeeRepository extends EmployeeRepository, Repository<Employee, Integer> {

	@Override
    @Query("SELECT DISTINCT employee FROM Employee employee WHERE employee.lastName LIKE :lastName%")
	public Collection<Employee> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT employee FROM Employee employee WHERE employee.id =:id")
	public Employee findById(@Param("id") int id);


	@Query("SELECT employee.requests FROM Employee employee WHERE employee.id=:id")
	public Set<Request> getRequests(@Param("id") int id);

	@Query("SELECT employee.id FROM Employee employee WHERE employee.user.username=:username")
	public Integer findByUsername(@Param("username") String username);
	
	@Query("SELECT emp,c FROM Employee emp,Clinic c WHERE emp in elements(c.employees) and c.id=:id")
    public Collection<Employee> findEmployeeByClinicId(@Param("id") int clinicId);
	@Query("SELECT emp,r FROM Employee emp,Residence r WHERE emp in elements(r.employees) and r.id=:id")
    public Collection<Employee> findEmployeeByResidenceId(@Param("id") int residenceId);


}
