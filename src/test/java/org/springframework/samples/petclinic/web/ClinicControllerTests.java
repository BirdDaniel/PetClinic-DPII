package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
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

import java.security.Principal;

/**
 * Test class for the {@link ClinicController}
 */
@WebMvcTest(controllers=ClinicController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ClinicControllerTests {
	
	@Autowired
	private ClinicController clinicController;

	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private ClinicService clinicService;

	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_OWNER_ID = 2;
	
	private Owner david;
		
	private Authorities auth;
	
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
		david = new Owner();
		david.setId(TEST_OWNER_ID);
		david.setFirstName("David");
		david.setLastName("Schroeder");
		david.setAddress("2749 Blackhawk Trail");
		david.setTelephone("6085559435");
		auth = new Authorities();
		auth.setAuthority("owner");
		auth.setUsername("owner2");
		given(this.authoritiesService.findById("owner2")).willReturn(auth);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);
		given(this.clinicService.findAll()).willReturn(Lists.newArrayList(c1, c2));
	}
      
    @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
	@Test
	void testShowClinicListHtml() throws Exception {
		mockMvc.perform(get("/clinic/findAll")).andExpect(status().isOk())
		.andExpect(view().name("services/clinics"))
		.andExpect(model().attributeExists("clinics"));
			
	}	

}
