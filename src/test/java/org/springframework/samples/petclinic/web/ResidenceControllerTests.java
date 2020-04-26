package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=ResidenceController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ResidenceControllerTests {
	@MockBean
	
    private ResidenceService residenceService;
	
	@Autowired 
	private ResidenceController residenceController;
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
	private static final int TEST_RESIDENCE_ID=1;
	private static final int  TEST_RESIDENCE_ID2=50;
	private Owner david;
	private Residence r1;
	@BeforeEach
	void setup() {
		Residence r1=new Residence();
		r1.setId( TEST_RESIDENCE_ID);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);
		given(this.ownerService.findOwnerByUsername("owner2")).willReturn(david);
		given(this.residenceService.findResidenceById(TEST_RESIDENCE_ID)).willReturn(r1);
	}
//	 @WithMockUser(value = "owner2")
//		@Test
//		void testShowResidenceListHtml() throws Exception {
//			mockMvc.perform(get("/residence/findAll")).andExpect(status().isOk())
//			.andExpect(view().name("services/residences"))
//			.andExpect(model().attributeExists("residences"));
//		}
}
