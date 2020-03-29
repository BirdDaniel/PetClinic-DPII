package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
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

	@Test
	void shouldFindById() {
		assertThat(this.employeeService.findEmployeeById(1)).isNotNull();
	}

	
	@Test
	void shouldNotFindById() {
		assertThat(this.employeeService.findEmployeeById(-1)).isNull();
	}

	@Test
	void shouldFindRequests() {
		assertThat(this.employeeService.getRequests(1)).isNotEmpty();
	}

	@Test
	void shouldNotFindRequests() {

		assertThat(this.employeeService.getRequests(24)).isEmpty();
		assertThat(this.employeeService.getRequests(-4)).isEmpty();

	}
	
	@Test
	void shouldFindRequestByEmployeeId() {
        
		Employee employee = this.employeeService.findEmployeeById(1);
		
		Collection<Request> req = this.employeeService.getRequests(employee.getId());
		
		assertThat(req.size()).isGreaterThanOrEqualTo(3);
    
	}

	@Test
	void saveEmployee(){

		User user = new User();
		user.setUsername("joselo");
		user.setPassword("pass");
		user.setEnabled(true);
		
		Authorities authority = new Authorities();
		authority.setUsername("joselo");
		authority.setAuthority("employee");

		Employee employee = new Employee();
		employee.setFirstName("firstName");
		employee.setLastName("LastName");
		employee.setTelephone("618596552");
		employee.setDni("15458405F");
		employee.setUser(user);

		this.employeeService.saveEmployee(employee);
		assertThat(this.employeeService.findEmployeesByLastName("LastName").size())
		.isEqualTo(1);
	
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