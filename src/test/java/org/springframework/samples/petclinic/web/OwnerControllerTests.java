package org.springframework.samples.petclinic.web;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers=OwnerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class OwnerControllerTests {

	private static final int TEST_OWNER_ID = 1;
	
	@Autowired
	private OwnerController ownerController;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private PetService petService;

	@MockBean
	private RequestService requestService;
	
	@Autowired
	private MockMvc mockMvc;

	private Owner david;
	
	@MockBean
	private Model model;
	
	private Authorities auth;
	
	private Pet leo;
	
	private User user;
	
	private Request reqEmpty;
	
	private Request reqClinic;
	
	private Request reqResidence;
	
	private Clinic clinic;
	
	private Residence residence;
	
	
	
	//'David', 'Schroeder','6085559435' , '2749 Blackhawk Trail', 'owner1');
	// pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
	//7, '2019-08-05 19:00', '2030-08-08 17:00', true, 1, 2, 2)
	//INSERT INTO requests VALUES (2, '2019-08-01 18:00', '2030-08-06 19:00', true, 2, 1, 3);
	//INSERT INTO requests VALUES (5, '2019-08-01 15:30', '2030-08-06 14:00', true, 3, 3, 3);
	//INSERT INTO clinics VALUES (1, 'Clinica 1','Elm Street s/n', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 1);
	//INSERT INTO residences VALUES (1, 'Residencia 1','Madison Square, 51-B', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 2);
	@BeforeEach
	void setup() {
		
		reqEmpty = new Request();
		reqEmpty.setId(TEST_REQUEST_ID_NULL);
		Calendar c = Calendar.getInstance();
		c.set(2019, 8, 5, 19, 00);
		reqEmpty.setRequestDate(c.getTime());
		c.set(2030, 8, 8, 17, 00);
		reqEmpty.setServiceDate(c.getTime());
		reqEmpty.setStatus(true);
		
		reqClinic = new Request();
		reqClinic.setId(TEST_REQUEST_ID_CLINIC);
		c.set(2019, 8, 1, 18, 00);
		reqClinic.setRequestDate(c.getTime());
		c.set(2030, 8, 6, 19, 00);
		reqClinic.setServiceDate(c.getTime());
		reqClinic.setStatus(true);
		
		reqResidence = new Request();
		reqResidence.setId(TEST_REQUEST_ID_RESIDENCE);
		c.set(2019, 8, 1, 15, 30);
		reqResidence.setRequestDate(c.getTime());
		c.set(2030, 8, 6, 14, 00);
		reqResidence.setServiceDate(c.getTime());
		reqResidence.setStatus(true);
		
		Set<Request> requestList = new HashSet<Request>(); 
		requestList.add(reqResidence);
		
		clinic = new Clinic();
		clinic.setId(TEST_CLINIC_ID);
		clinic.setName("Clinica 1");
		clinic.setAddress("Elm Street s/n");
		clinic.setOpen(LocalTime.of(10, 10));
		clinic.setClose(LocalTime.of(12, 00));
		clinic.setRating(3);
		clinic.setDescription("Description 1");
		clinic.setMax(10);
		clinic.setPrice(2.5);
//		clinic.addRequest(reqClinic);
		
		residence = new Residence();
		residence.setId(TEST_RESIDENCE_ID);
		residence.setName("Residencia 1");
		residence.setAddress("Madison Square, 51-B");
		residence.setOpen(LocalTime.of(10, 10));
		residence.setClose(LocalTime.of(12, 00));
		residence.setRating(3);
		residence.setDescription("Description 1");
		residence.setMax(10);
		residence.setPrice(2.5);
		residence.setRequests(requestList);
	
		
		leo = new Pet();
		LocalDate fecha = LocalDate.of(2010, 9, 7);
		PetType petType = new PetType();
		leo.setId(1);
		leo.setName("Leo");
		leo.setBirthDate(fecha);
		
//		User user = new User();
//		user.setUsername("david");
//		user.setPassword("1234");
//		user.setEnabled(true);
		user = new User();
		user.setUsername("owner2");
		user.setPassword("0wn3r");
		david = new Owner();
		david.setId(TEST_OWNER_ID);
		david.setFirstName("Peter");
		david.setLastName("McTavish");
		david.setAddress("2387 S. Fair Way");
		david.setTelephone("6085552765");
		david.setUser(user);
		david.setRequests(requestList);
//		david.addPet(leo);
		
		auth = new Authorities();
		auth.setAuthority("owner");
		auth.setUsername("owner2");
		
		model.addAttribute("loggedUser", 2);

//	@BeforeEach
//	void setup(){

//		david = new Owner();
//		david.setId(TEST_OWNER_ID);
//		david.setFirstName("firstName");
//		david.setLastName("LastName");
//		david.setAddress("addressDavid");
//		david.setTelephone("645789456");
		
		given(this.ownerService.findIdByUsername("owner1")).willReturn(1);
		given(this.ownerService.findIdByUsername("owner2")).willReturn(2);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);

	}
	
	@WithMockUser(value = "owner1")
	@Test
	void shouldGetOwnerDetails() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownerDetails"));
	}

// TEST DE PET LIST
//		mockMvc.perform(get("/owners/{ownerId}/myPetList",  TEST_OWNER_ID).param("name", "Unknown name"))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeHasFieldErrors("pet", "name"))
//		.andExpect(model().attributeHasFieldErrorCode("pet", "name", "notFound"))
//		.andExpect(view().name("/owners/myPetList"));
//	}
        

        
        @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
        @Test
	void testProcessFindRequestFormSuccess() throws Exception {
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david,new Owner());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(model().attribute("owner", hasProperty("lastName", is("McTavish"))))
		.andExpect(model().attribute("owner", hasProperty("firstName", is("Peter"))))
		.andExpect(model().attribute("owner", hasProperty("address", is("2387 S. Fair Way"))))
		.andExpect(model().attribute("owner", hasProperty("telephone", is("6085552765"))))
		.andExpect(view().name("owners/myRequestList"));
	}
        
        
        @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
        @Test
	void testProcessFindRequestDetailsFormSuccess() throws Exception {
		given(this.requestService.findById(TEST_REQUEST_ID_NULL)).willReturn(reqEmpty, new Request());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_NULL))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
		
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_ID_CLINIC))).willReturn(clinic, new Clinic());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_CLINIC))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("clinic"))
//		.andExpect(model().attribute("clinic", hasProperty("rating", is("3"))))
		.andExpect(view().name("services/clinicServiceDetails"));
		
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE))).willReturn(residence, new Residence());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_RESIDENCE))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("residence"))
//		.andExpect(model().attribute("residence", hasProperty("name", is("Residencia 1"))))
//		.andExpect(model().attribute("residence", hasProperty("rating", is("3"))))
//		.andExpect(model().attribute("residence", hasProperty("address", is("Madison Square, 51-B"))))
//		.andExpect(model().attribute("residence", hasProperty("close", is("10:10"))))
//		.andExpect(model().attribute("residence", hasProperty("open", is("12:00"))))
//		.andExpect(model().attribute("residence", hasProperty("description", is("Description 1"))))
//		.andExpect(model().attribute("residence", hasProperty("max", is("10"))))
//		.andExpect(model().attribute("residence", hasProperty("price", is("2.5"))))
//		.andExpect(model().attribute("residence", hasProperty("days", is("2"))))
		.andExpect(view().name("services/residenceServiceDetails"));
		
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE))).willReturn(null, new Clinic());
		
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_ID_CLINIC))).willReturn(null, new Residence());
	}
        
        @WithMockUser(value = "owner2" , username = "owner2" ,password = "0wn3r",authorities = {"owner"})
        @Test
	void testProcessFindRequestResidenceFormSuccess() throws Exception {
	//	given(this.requestService.findAcceptedResByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(reqResidence, new Request()));
		mockMvc.perform(get("/owners/{ownerId}/myPetList/residence",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("requests"))
//		.andExpect(model().attribute("request", hasProperty("requestDate", is("2019-08-05 16:00"))))
//		.andExpect(model().attribute("request", hasProperty("serviceDate", is("2030-08-06 20:00"))))
		.andExpect(view().name("owners/myPetResidence"));
	}
        
}
