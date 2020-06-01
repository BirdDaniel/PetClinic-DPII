package org.springframework.samples.petclinic.dbintegration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestPropertySource(locations = "classpath:application-mysql.properties")
class ResidenceServiceTest {

	@Autowired
    protected ResidenceService residenceService;
	
	@Test
	void shouldFindAllResidences() {
		Collection<Residence> residences = (Collection<Residence>) this.residenceService.findAll();

		Residence r1 = EntityUtils.getById(residences, Residence.class, 1);
		assertThat(r1.getName()).isEqualTo("Residence \"Happy Pet\"");
		Residence r2 = EntityUtils.getById(residences, Residence.class, 3);
		assertThat(r2.getName()).isEqualTo("Pet Stay");
	}
}
