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
package org.springframework.samples.petclinic.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "employees")
public class Employee extends Person {
	
	//@ManyToOne()
	//@JoinColumn(name="service_id")
	//private BaseClinic service;

	@Pattern(regexp="^\\d{8}[A-Z]$", message = "DNI doesn't have correct format")
	@NotEmpty
	protected String dni;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "employee")
	private Set<Request> requests;

//	public BaseClinic getServices() {
//		return service;
//	}
//
//	public void setServices(BaseClinic service) {
//		this.service = service;
//	}
	
	

}
