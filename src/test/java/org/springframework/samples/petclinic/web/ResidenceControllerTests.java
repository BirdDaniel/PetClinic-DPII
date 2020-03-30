package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Residence;

import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;

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
@WebMvcTest(controllers=ResidenceController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ResidenceControllerTests {

	@Autowired
	private ResidenceController residenceController;

	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private AuthoritiesService authoritiesService;


	@MockBean
	private ResidenceService residenceService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		Residence r1 = new Residence();
		r1.setName("r1");
		r1.setId(Integer.valueOf(1));
		r1.setAddress("a1");
		Residence r2 = new Residence();
		r2.setName("r2");
		r2.setId(Integer.valueOf(2));
		r2.setAddress("a2");
		given(this.residenceService.findAll()).willReturn(Lists.newArrayList(r1, r2));
	}
      
    @WithMockUser(value = "spring")
		@Test
	void testShowReidenceListHtml() throws Exception {
		mockMvc.perform(get("/residence/findAll")).andExpect(status().isOk()).andExpect(model().attributeExists("residences"))
				.andExpect(view().name("services/residences"));
	}	

}
