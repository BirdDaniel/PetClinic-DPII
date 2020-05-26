package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ClinicServiceTest {

	@Autowired
    protected ClinicService clincService;
	@Autowired
	protected RequestService requestService;
	@Autowired EmployeeService employeeService;
	
	@Test
	void shouldFindAllClinc() {
		Collection<Clinic> residences = (Collection<Clinic>) this.clincService.findAll();

		Clinic r1 = EntityUtils.getById(residences, Clinic.class, 1);
		assertThat(r1.getName()).isEqualTo("Clinica 1");
		Clinic r2 = EntityUtils.getById(residences, Clinic.class, 3);
		assertThat(r2.getName()).isEqualTo("Clinica 3");
	}

	
	@Test
	void shouldFinAllClinic() {
		assertThat(this.clincService.findAll()).isNotNull();
	}
	
	
	@Test
	void shouldFindClinicById() {
		assertThat(this.clincService.findClinicById(1)).isNotNull();
	}
	@Test
	void shouldNotFindClinicById() {
		assertThat(this.clincService.findClinicById(-1)).isNull();
	}
	@Test
	void shouldFindClinicByRequest() {
		Request request = this.requestService.findById(1);
		assertThat(this.clincService.findClinicByRequest(request)).isNotNull();
	}
	@Test
	void shouldNotFindClinicByRequest() {
		Request request = this.requestService.findById(-1);
		assertThat(this.clincService.findClinicByRequest(request)).isNull();
	}
	@Test
	void shouldFindClinicByEmployee() {
		Employee employee=this.employeeService.findEmployeeById(1);
		assertThat(this.clincService.findByEmployee(employee)).isNotNull();
	}
	@Test
	void shouldNotFindClinicByEmployee() {
		Employee employee=this.employeeService.findEmployeeById(-1);
		assertThat(this.clincService.findByEmployee(employee)).isNull();
	}
	@Test
	void shouldFindEmployeeById() {
		assertThat(this.employeeService.findEmployeeById(1)).isNotNull();
	}
	@Test
	void shouldNotFindEmployeeById() {
		assertThat(this.employeeService.findEmployeeById(-1)).isNull();
	}
}



