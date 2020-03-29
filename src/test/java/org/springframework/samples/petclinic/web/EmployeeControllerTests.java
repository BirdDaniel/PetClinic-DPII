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
import org.springframework.samples.petclinic.model.Employee;
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
	
	@WebMvcTest(controllers=EmployeeController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration= SecurityConfiguration.class)
	class EmployeeControllerTests {
	
	
	private static final int TEST_REQUEST_ID_CLINIC = 2;
	
	private static final int TEST_REQUEST_ID_RESIDENCE = 5;
	
	private static final int TEST_CLINIC_ID = 1;
	
	private static final int TEST_RESIDENCE_ID = 1;
	
	private static final int TEST_EMPLOYEE_ID = 1;
	
	@Autowired
	private EmployeeController employeeController;
	
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
	
	private Pet leo;
	
	private Request reqClinic;
	
	private Request reqResidence;
	
	private Clinic clinic;
	
	private Residence residence;
	
	private Employee employee;
	
	//'David', 'Schroeder','6085559435' , '2749 Blackhawk Trail', 'owner1');
	// pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
	//7, '2019-08-05 19:00', '2030-08-08 17:00', true, 1, 2, 2)
	//INSERT INTO requests VALUES (2, '2019-08-01 18:00', '2030-08-06 19:00', true, 2, 1, 3);
	//INSERT INTO requests VALUES (5, '2019-08-01 15:30', '2030-08-06 14:00', true, 3, 3, 3);
	//INSERT INTO clinics VALUES (1, 'Clinica 1','Elm Street s/n', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 1);
	//INSERT INTO employees VALUES (1, 'Marta', 'Carter', '679845125','65847525H','emp1');
	//INSERT INTO residences VALUES (1, 'Residencia 1','Madison Square, 51-B', '2012-06-08 10:10:10', '2022-06-08 12:00', 3, 'Description 1', 10, 2.5, 2);
	@BeforeEach
	void setup() {
		
	employee = new Employee();
	employee.setId(TEST_EMPLOYEE_ID);
	employee.setDni("65847525H");
	employee.setFirstName("Marta");
	employee.setLastName("Carter");
	employee.setTelephone("679845125");
		
	
	Calendar c = Calendar.getInstance();
	
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
	
	//david.addPet(leo);;
	given(this.petService.findPetById(1)).willReturn(leo);
	given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(petType));
	given(this.requestService.findById(TEST_REQUEST_ID_CLINIC)).willReturn(reqClinic);
	given(this.requestService.findById(TEST_REQUEST_ID_RESIDENCE)).willReturn(reqResidence);
	given(this.clinicService.findClinicById(TEST_CLINIC_ID)).willReturn(clinic);
	given(this.residenceService.findResidenceById(TEST_RESIDENCE_ID)).willReturn(residence);
	given(this.employeeService.findEmployeeById(TEST_EMPLOYEE_ID)).willReturn(employee);
	
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testViewRequestsForm() throws Exception {
	mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("requests"))
			.andExpect(view().name("employees/requests"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testAcceptRequestForm() throws Exception {
	mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/accept", TEST_EMPLOYEE_ID, TEST_REQUEST_ID_RESIDENCE)).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/employees/{employeeId}/requests"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeclineRequestForm() throws Exception {
	mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID_RESIDENCE)).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/employees/{employeeId}/requests"));
	}
}
