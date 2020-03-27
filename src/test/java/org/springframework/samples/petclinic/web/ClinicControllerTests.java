package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link ResidenceController}
 */
@WebMvcTest(controllers=ClinicController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ClinicControllerTests {

	@Autowired
	private ClinicController clinicController;

	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		Clinic c1 = new Clinic();
		c1.setName("c1");
		c1.setId(Integer.valueOf(1));
		c1.setAddress("a1");
		Clinic c2 = new Clinic();
		c2.setName("c2");
		c2.setId(Integer.valueOf(2));
		c2.setAddress("a2");
		given(this.clinicService.findAll()).willReturn(Lists.newArrayList(c1, c2));
	}
      
    @WithMockUser(value = "spring")
		@Test
	void testShowReidenceListHtml() throws Exception {
		mockMvc.perform(get("/clinic/findAll")).andExpect(status().isOk()).andExpect(model().attributeExists("clinics"))
				.andExpect(view().name("services/clinics"));
	}	

}
