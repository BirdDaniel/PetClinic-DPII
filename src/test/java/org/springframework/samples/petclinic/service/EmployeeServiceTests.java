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
import org.springframework.samples.petclinic.model.Request;
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

}