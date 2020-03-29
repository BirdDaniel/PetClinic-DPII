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


/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers=OwnerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class OwnerControllerTests {

	private static final int TEST_OWNER_ID = 2;
	
	private static final int TEST_REQUEST_ID_NULL = 7;
	
	private static final int TEST_REQUEST_ID_CLINIC = 2;
	
	private static final int TEST_REQUEST_ID_RESIDENCE = 5;
	
	private static final int TEST_CLINIC_ID = 1;
	
	private static final int TEST_RESIDENCE_ID = 1;

	
	@Autowired
	private OwnerController ownerController;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private RequestService requestService;
	
	@MockBean
	private ClinicService clinicService;

	@MockBean
	private ResidenceService residenceService;
	
	@MockBean
	private PetService petService;
        
    @MockBean
	private UserService userService;
        
    @MockBean
    private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Owner david;
	
	private Pet leo;
	
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
	//	clinic.addRequest(reqClinic);
		
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
	//	residence.addRequest(reqResidence);
		
		
		leo = new Pet();
		LocalDate fecha = LocalDate.of(2010, 9, 7);
		PetType petType = new PetType();
		leo.setId(1);
		leo.setName("Leo");
		leo.setBirthDate(fecha);
		
		User user = new User();
//		user.setUsername("david");
//		user.setPassword("1234");
//		user.setEnabled(true);
		david = new Owner();
		david.setId(TEST_OWNER_ID);
		david.setFirstName("David");
		david.setLastName("Schroeder");
		david.setAddress("2749 Blackhawk Trail");
		david.setTelephone("6085559435");
		david.setUser(user);
		
		Authorities authority = new Authorities();
		
		authority.setUsername("david");
		authority.setAuthority("owner");
//		david.addPet(leo);;
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);
		given(this.petService.findPetById(1)).willReturn(leo);
		given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(petType));
		given(this.requestService.findById(TEST_REQUEST_ID_NULL)).willReturn(reqEmpty);
		given(this.requestService.findById(TEST_REQUEST_ID_CLINIC)).willReturn(reqClinic);
		given(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE)).willReturn(reqResidence);
		given(this.clinicService.findClinicById(TEST_CLINIC_ID)).willReturn(clinic);
		given(this.residenceService.findResidenceById(TEST_RESIDENCE_ID)).willReturn(residence);

	}

	@WithMockUser(value = "spring")
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs")
							.with(csrf())
							.param("address", "123 Caramel Street")
							.param("telephone", "01316761638"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/new")
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/findOwners"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormSuccess() throws Exception {
		given(this.ownerService.findOwnerByLastName("")).willReturn(Lists.newArrayList(david, new Owner()));

		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.ownerService.findOwnerByLastName(david.getLastName())).willReturn(Lists.newArrayList(david));

		mockMvc.perform(get("/owners").param("lastName", "Schroeder")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Unknown Surname")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
				.andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
				.andExpect(view().name("owners/findOwners"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"))
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Schroeder"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("David"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("2749 Blackhawk Trail"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("6085559435"))))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("address", "123 Caramel Street")
							.param("telephone", "01616291589"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", is("Schroeder"))))
				.andExpect(model().attribute("owner", hasProperty("firstName", is("David"))))
				.andExpect(model().attribute("owner", hasProperty("address", is("2749 Blackhawk Trail"))))
				.andExpect(model().attribute("owner", hasProperty("telephone", is("6085559435"))))
				.andExpect(view().name("owners/ownerDetails"));
	}
     
    //===============================================================================================================
       
        @WithMockUser(value = "spring")
        @Test
	void testProcessFindPetsFormSuccess() throws Exception {
		given(this.petService.findPetsOfOwnerByName(TEST_OWNER_ID, "")).willReturn(Lists.newArrayList(leo, new Pet()));

		mockMvc.perform(get("/owners/{ownerId}/myPetList",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("/owners/myPetList"));
	}

        
        @WithMockUser(value = "spring")
        @Test
	void testProcessFindRequestFormSuccess() throws Exception {
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david, new Owner());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList",  TEST_OWNER_ID)).andExpect(status().isOk()).andExpect(view().name("owners/myRequestList"));
	}
        
        @WithMockUser(value = "spring")
        @Test
	void testProcessFindRequestDetailsFormSuccess() throws Exception {
		given(this.requestService.findById(TEST_REQUEST_ID_NULL)).willReturn(reqEmpty, new Request());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_NULL))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
		
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_ID_CLINIC))).willReturn(clinic, new Clinic());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_CLINIC))
		.andExpect(status().isOk())
		.andExpect(view().name("services/clinicServiceDetails"));
		
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE))).willReturn(residence, new Residence());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_ID_RESIDENCE))
		.andExpect(status().isOk())
		.andExpect(view().name("services/residenceServiceDetails"));
		
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE))).willReturn(null, new Clinic());
		
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_ID_CLINIC))).willReturn(null, new Residence());
	}
        
        @WithMockUser(value = "spring")
        @Test
	void testProcessFindRequestResidenceFormSuccess() throws Exception {
		given(this.requestService.findAcceptedResByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(reqResidence, new Request()));
		mockMvc.perform(get("/owners/{ownerId}/myPetList/residence",  TEST_OWNER_ID)).andExpect(status().isOk()).andExpect(view().name("owners/myPetResidence"));
	}
}
