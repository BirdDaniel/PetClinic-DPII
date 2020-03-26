package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class EmployeeServiceTests {                
        
    @Autowired
    protected EmployeeService employeeService;

	@Test
	void shouldFindEmployeesByLastName() {
        
		Collection<Employee> employees = this.employeeService.findEmployeesByLastName("Carter");
		assertThat(employees.size()).isEqualTo(1);

		employees = this.employeeService.findEmployeesByLastName("Daviss");
        assertThat(employees.isEmpty()).isTrue();
        
		employees = this.employeeService.findEmployeesByLastName("Cart");
		assertThat(employees.size()).isEqualTo(1);
		

	}
/*
	@Test
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.employeeService.findOwnerById(1);
		assertThat(owner.getLastName()).startsWith("Franklin");
		assertThat(owner.getPets().size()).isEqualTo(1);
		assertThat(owner.getPets().get(0).getType()).isNotNull();
		assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
	}
/*
	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.employeeService.findOwnerByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		//owner.setAddress("4, Evans Street");
		//owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
                User user=new User();
                user.setUsername("Sam");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                owner.setUser(user);                
                
		this.employeeService.saveOwner(owner);
		assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.employeeService.findOwnerByLastName("Schultz");
		assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Owner owner = this.employeeService.findOwnerById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.employeeService.saveOwner(owner);

		// retrieving new name from database
		owner = this.employeeService.findOwnerById(1);
		assertThat(owner.getLastName()).isEqualTo(newLastName);
	}
*/

}