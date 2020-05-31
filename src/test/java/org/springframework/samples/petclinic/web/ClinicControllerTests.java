package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;

import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
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
	private static final int TEST_CLINIC_ID=1;
	private static final int TEST_CLINIC_ID2=50;
	private Owner david;
		
	@BeforeEach
	void setup() {
		Clinic c1= new Clinic();
		c1.setId(TEST_CLINIC_ID);
		david = new Owner();
		david.setId(TEST_OWNER_ID);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);
		given(this.ownerService.findOwnerByUsername("owner2")).willReturn(david);
		given(this.clinicService.findClinicById(TEST_CLINIC_ID)).willReturn(c1);
	}
      
    @WithMockUser(value = "owner2")
	@Test
	void testShowClinicListHtml() throws Exception {
		mockMvc.perform(get("/clinic/findAll")).andExpect(status().isOk())
		.andExpect(view().name("services/clinics"))
		.andExpect(model().attributeExists("clinics"))
		.andExpect(model().attributeExists("loggedUser"));
    }
    @WithMockUser(value="owner2")
    @Test
    void testShowClinicPos() throws Exception {
    	 mockMvc.perform(get("/clinic/{clinicId}", TEST_CLINIC_ID))
    	 .andExpect(status().isOk())
    	 .andExpect(model().attributeExists("clinic"))
 		 .andExpect(model().attributeExists("loggedUser"))
    	 .andExpect(view().name("services/clinicServiceDetails"));
    }	
		
    @WithMockUser(value="owner2")
    @Test
  
    void testShowClinicNeg() throws Exception {
    	 mockMvc.perform(get("/clinic/{clinicId}", TEST_CLINIC_ID2))
    	 .andExpect(status().is3xxRedirection())
 		.andExpect(view().name("redirect:/oups"));
 	}

}
