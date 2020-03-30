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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalTime;

/**
 * Test class for the {@link ResidenceController}
 */
@WebMvcTest(controllers=ResidenceController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ResidenceControllerTests {
	private static final int TEST_RESIDENCE_ID = 1;
	private static final int TEST_RESIDENCE_ID2 = 2;
	private static final int TEST_RESIDENCE_ID3 = 3;
	private static final int TEST_RESIDENCE_ID4 = 4;
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
	private Residence residencia1;
	private Residence residencia2;
	private Residence residencia3;
	private Residence residencia4;
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
		residencia1 = new Residence();
		residencia1.setId(TEST_RESIDENCE_ID);
		residencia1.setName("Residencia 1");
		residencia1.setAddress("Madison Square, 51-B");

		LocalTime close = LocalTime.of(10, 10, 10);
		LocalTime open=LocalTime.of(12, 00, 00);
		residencia1.setClose(close);
		residencia1.setOpen(open);
		residencia1.setRating(3);
		residencia1.setDescription("Description 1");
		residencia1.setMax(10);
		residencia1.setPrice(2.5);
		residencia1.setDays(2);
		given(this.residenceService.findResidenceById(TEST_RESIDENCE_ID)).willReturn(residencia1);


	}  
	 @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
	@Test
	void testShowResidencePos1() throws Exception {
		mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID)).andExpect(status().isOk())
			.andExpect(model().attributeExists("residence"))
				.andExpect(model().attribute("residence", hasProperty("name", is("Residencia 1"))))
				.andExpect(model().attribute("residence", hasProperty("address", is("Madison Square, 51-B"))))
				.andExpect(model().attribute("residence", hasProperty("close", is(LocalTime.of(10, 10, 10)))))
				.andExpect(model().attribute("residence", hasProperty("open", is(LocalTime.of(12, 00, 00)))))
				.andExpect(model().attribute("residence", hasProperty("rating", is(3))))
				.andExpect(model().attribute("residence", hasProperty("description", is("Description 1"))))
				.andExpect(model().attribute("residence", hasProperty("max", is(10))))
				.andExpect(model().attribute("residence", hasProperty("days", is(2))))
				.andExpect(view().name("residence/residenceServiceDetails"));
	}
	//Positivo

	   @WithMockUser(value = "spring")
		@Test
		void testShowResidencePos2() throws Exception {
			mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID2)).andExpect(status().isOk())
					.andExpect(model().attribute("residence", hasProperty("address", is("a2"))))
					.andExpect(model().attribute("residence", hasProperty("price", is(3.0))))
					.andExpect(model().attribute("residence", hasProperty("ratin", is(1))))
					.andExpect(model().attribute("residence", hasProperty("max", is(7))))
					.andExpect(model().attribute("residence", hasProperty("description", is("Description R2"))))
					.andExpect(model().attribute("residence", hasProperty("days", is(5))))
					.andExpect(view().name("residence/residenceServiceDetails"));
		}
//Negativo
	   //Descripcion no concuerda
	   @WithMockUser(value = "spring")
		@Test
		void testShowResidenceNeg() throws Exception {
			mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID)).andExpect(status().isNotAcceptable())
					.andExpect(model().attribute("residence", hasProperty("address", is("a1"))))
					.andExpect(model().attribute("residence", hasProperty("price", is(1.5))))
					.andExpect(model().attribute("residence", hasProperty("ratin", is(2))))
					.andExpect(model().attribute("residence", hasProperty("max", is(10))))
					.andExpect(model().attribute("residence", hasProperty("description", is("Description Mal"))))
					.andExpect(model().attribute("residence", hasProperty("days", is(2))))
					.andExpect(view().name("residence/residenceServiceDetails"));
		}
//Rating superior a 5
	   @WithMockUser(value = "spring")
		@Test
		void testShowResidenceNegRating() throws Exception {
			mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID3)).andExpect(status().isNotAcceptable())
					.andExpect(model().attribute("residence", hasProperty("address", is("a1"))))
					.andExpect(model().attribute("residence", hasProperty("price", is(1.5))))
					.andExpect(model().attribute("residence", hasProperty("ratin", is(6))))
					.andExpect(model().attribute("residence", hasProperty("max", is(10))))
					.andExpect(model().attribute("residence", hasProperty("description", is("Description R1"))))
					.andExpect(model().attribute("residence", hasProperty("days", is(2))))
					.andExpect(view().name("residence/residenceServiceDetails"));
		}

	 //Days=0
	   @WithMockUser(value = "spring")
		@Test
		void testShowResidenceNegDays() throws Exception {
			mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID4)).andExpect(status().isNotAcceptable())
					.andExpect(model().attribute("residence", hasProperty("address", is("a1"))))
					.andExpect(model().attribute("residence", hasProperty("price", is(1.5))))
					.andExpect(model().attribute("residence", hasProperty("ratin", is(2))))
					.andExpect(model().attribute("residence", hasProperty("max", is(10))))
					.andExpect(model().attribute("residence", hasProperty("description", is("Description R1"))))
					.andExpect(model().attribute("residence", hasProperty("days", is(0))))
					.andExpect(view().name("residence/residenceServiceDetails"));
		}

	
	

      
	 @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
		@Test
	void testShowReidenceListHtml() throws Exception {
		mockMvc.perform(get("/residence/findAll")).andExpect(status().isOk()).andExpect(model().attributeExists("residences"))
				.andExpect(view().name("services/residences"));
	}	

}
