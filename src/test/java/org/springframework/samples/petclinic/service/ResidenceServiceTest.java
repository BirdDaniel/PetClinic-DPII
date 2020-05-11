package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ResidenceServiceTest {

	@Autowired
    protected ResidenceService residenceService;
	@Autowired 
	protected RequestService requestService;
	@Autowired
	protected EmployeeService employeeService;
	@Test
	void shouldFindAllResidences() {
		Collection<Residence> residences = (Collection<Residence>) this.residenceService.findAll();

		Residence r1 = EntityUtils.getById(residences, Residence.class, 1);
		assertThat(r1.getName()).isEqualTo("Residence \"Happy Pet\"");
		Residence r2 = EntityUtils.getById(residences, Residence.class, 3);
		assertThat(r2.getName()).isEqualTo("Pet Stay");
	}
	@Test
	void shouldFindAllResidences1() {
		assertThat(this.residenceService.findAll()).isNotNull();
	}
	@Test
	void shouldFindResidenceById() {
		assertThat(this.residenceService.findResidenceById(1)).isNotNull();
	}
	@Test
	void shouldNotFindResidenceById() {
		assertThat(this.residenceService.findResidenceById(-1)).isNull();
	}
	@Test
	void shouldFindByEmployee() {
		Employee employee= this.employeeService.findEmployeeById(2);
		assertThat(this.residenceService.findByEmployee(employee)).isNotNull();
	}
	@Test
	void shouldNotFindByEmployee() {
		Employee employee= this.employeeService.findEmployeeById(-1);
		assertThat(this.residenceService.findByEmployee(employee)).isNull();
	}
	@Test
	void shouldFindResidenceByRequest() {
		Request request = this.requestService.findById(4);
		assertThat(this.residenceService.findResidenceByRequest(request)).isNotNull();
	}
	@Test
	//3 no tiene asignada
	void shouldFindNotResidenceByRequestAssign() {
		Request request = this.requestService.findById(3);
		assertThat(this.residenceService.findResidenceByRequest(request)).isNull();
	}
	@Test
	void shouldNotFindResidenceByRequest() {
		Request request = this.requestService.findById(-1);
		assertThat(this.residenceService.findResidenceByRequest(request)).isNull();
	}
	@Test
	void shouldFindResidenceByEmployee() {
		Employee employee=this.employeeService.findEmployeeById(1);
		assertThat(this.residenceService.findByEmployee(employee)).isNotNull();
	}
	@Test
	void shouldNotFindResidenceByEmployee() {
		Employee employee=this.employeeService.findEmployeeById(-1);
		assertThat(this.residenceService.findByEmployee(employee)).isNull();
	}
	@Test
	void shouldFindReqsResidence() {
		assertThat(this.residenceService.findReqsResidence()).isNotNull();
	}
}
