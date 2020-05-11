package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
	
	@WebMvcTest(controllers=EmployeeController.class,
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
				excludeAutoConfiguration= SecurityConfiguration.class)
	class EmployeeControllerTests {
	
	private static final int TEST_EMPLOYEE_ID = 1;
	private static final int TEST_REQUEST_ID = 1;
	
	@Autowired
	private EmployeeController employeeController;
	
	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private ClinicService clinicService;

	@MockBean
	private ResidenceService residenceService;

	@MockBean
	private ItemService itemService;

	@MockBean
	private RequestService requestService;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup(){

		Employee george = new Employee();
		george.setId(TEST_EMPLOYEE_ID);

		Request request = new Request();
		request.setId(TEST_REQUEST_ID);

		// MOCK de los Services
		given(this.employeeService.findByUsername("emp1")).willReturn(1);
		given(this.employeeService.findByUsername("emp2")).willReturn(2);
		given(this.employeeService.findEmployeeById(TEST_EMPLOYEE_ID)).willReturn(george);

		given(this.requestService.findById(TEST_REQUEST_ID)).willReturn(request);

	}


	//-----------------------------------REQUESTS:START-----------------------//
	@WithMockUser(value = "emp1")
	@Test
	void shouldGetRequests() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("employees/requests"));
	}

	@WithMockUser(value = "emp2")
	@Test
	void shouldNotGetRequests() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	//-----------------------------------REQUESTS:END-----------------------//

	//----------------------------------APPOINTMENTS:START--------------------//
	@WithMockUser(value="emp1")
	@Test
	void shouldGetAppointments() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/appointments", TEST_EMPLOYEE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("employees/appointments"));
	}

	@WithMockUser(value="emp2")
	@Test
	void shouldNotGetAppointments() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/appointments", TEST_EMPLOYEE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	//----------------------------------APPOINTMENTS:END--------------------//

	//---------------------------------ACCEPT&DECLINE:START-----------------//
	@WithMockUser(value="emp1")
	@Test
	void shouldAcceptRequest() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/accept", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/requests"));
	}

	@WithMockUser(value="emp2")
	@Test
	void shouldNotAcceptRequest() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/accept", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	@WithMockUser(value="emp1")
	@Test
	void shouldDeclineRequest() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/requests"));
	}

	@WithMockUser(value="emp2")
	@Test
	void shouldNotDeclineRequest() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	@WithMockUser(value="emp1")
	@Test
	void shouldDeclineAppointment() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/appointments/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/appointments"));
	}

	@WithMockUser(value="emp2")
	@Test
	void shouldNotDeclineAppointment() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/appointments/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
}

