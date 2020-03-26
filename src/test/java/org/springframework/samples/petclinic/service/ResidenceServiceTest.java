package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ResidenceServiceTest {

	@Autowired
    protected ResidenceService residenceService;
	
	@Test
	void shouldFindAllResidences() {
		Collection<Residence> residences = (Collection<Residence>) this.residenceService.findAll();

		Residence r1 = EntityUtils.getById(residences, Residence.class, 1);
		assertThat(r1.getName()).isEqualTo("Residencia 1");
		Residence r2 = EntityUtils.getById(residences, Residence.class, 3);
		assertThat(r2.getName()).isEqualTo("Residencia 3");
	}
}
