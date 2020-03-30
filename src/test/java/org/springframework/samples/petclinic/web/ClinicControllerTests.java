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


import org.springframework.samples.petclinic.model.Residence;

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


import java.security.Principal;
import java.util.Calendar;

/**
 * Test class for the {@link ClinicController}
=======
/**
 * Test class for the {@link ResidenceController}

 */
@WebMvcTest(controllers=ClinicController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ClinicControllerTests {
	private static final int TEST_CLINIC_ID=1;
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
	
	private Clinic c1;
	private Clinic c2;
	private Clinic clinic1;
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
		clinic1=new Clinic();
		clinic1.setId(TEST_CLINIC_ID);
		clinic1.setName("Residencia");
		clinic1.setAddress("a1");
		clinic1.setDescription("Esto es una residencia");
		clinic1.setMax(10);

		Calendar c = Calendar.getInstance();
		c.set(2019, 8, 5, 19, 00);
		
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
			


		given(this.clinicService.findAll()).willReturn(Lists.newArrayList(c1, c2));
	}
	//Positivo
//    @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
//		@Test
//		void testShowClinicPos() throws Exception {
//    	
//		   mockMvc.perform(get("/clinic/{clinicId}", TEST_CLINIC_ID)).andExpect(status().isOk())
//		   .andExpect(model().attributeExists("clinic"))
//			.andExpect(model().attribute("clinic", hasProperty("name", is("Residencia"))))
//			.andExpect(model().attribute("clinic", hasProperty("address",is("a1"))))
//			.andExpect(model().attribute("clinic", hasProperty("description",is("Esto es una residencia"))))
//			.andExpect(model().attribute("clinic", hasProperty("max",is("10"))))
//			//.andExpect(model().attribute("clinic", hasProperty("open", is())))
//			
//			given(this.clinicService.findClinicById(TEST_CLINIC_ID)).willReturn(clinic1);
//
//    }
    @WithMockUser(value = "spring")
		@Test
	void testShowReidenceListHtml() throws Exception {
		mockMvc.perform(get("/clinic/findAll")).andExpect(status().isOk()).andExpect(model().attributeExists("clinics"))
				.andExpect(view().name("services/clinics"));

	}	

}
