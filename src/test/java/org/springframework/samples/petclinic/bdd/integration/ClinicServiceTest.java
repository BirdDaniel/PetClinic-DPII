package org.springframework.samples.petclinic.bdd.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestPropertySource(locations = "classpath:application-mysql.properties")
class ClinicServiceTest {

	@Autowired
    protected ClinicService clincService;
	
	@Test
	void shouldFindAllClinc() {
		Collection<Clinic> residences = (Collection<Clinic>) this.clincService.findAll();

		Clinic r1 = EntityUtils.getById(residences, Clinic.class, 1);
		assertThat(r1.getName()).isEqualTo("Clinica 1");
		Clinic r2 = EntityUtils.getById(residences, Clinic.class, 3);
		assertThat(r2.getName()).isEqualTo("Clinica 3");
	}
}