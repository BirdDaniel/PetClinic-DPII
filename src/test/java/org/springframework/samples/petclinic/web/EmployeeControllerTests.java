package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;



@WebMvcTest(controllers=EmployeeController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTests {

	private static final int TEST_EMPLOYEE_ID = 1;
	private static final int TEST_REQUEST_ID = 1;

	
	@MockBean
	private EmployeeService employeeService;
		
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private RequestService reqService;
	
	@MockBean
	private AuthoritiesService authService;
	
	@Mock
	private Request req1;
	@Mock
	private Request req2;
	@Mock
	private Request req3;
	@Mock
	private Request req4;

	private Request req5;

	@Mock
	Map<String, Object> model;

	@Autowired
	private MockMvc mockMvc;

	private Employee george;

	@BeforeEach
	void setup() {

        User user = new User();
        user.setUsername("username");
        user.setPassword("pass");
        user.setEnabled(true);

        Authorities auth = new Authorities();
        auth.setUsername(user.getUsername());
        auth.setAuthority("employee");

		george = new Employee();
		george.setId(TEST_EMPLOYEE_ID);
		george.setFirstName("George");
        george.setLastName("Franklin");
        george.setDni("15432567D");
        george.setTelephone("6085551023");
        george.setUser(user);

		Set<Request> reqs = new HashSet<>();
		reqs.add(req1);
		reqs.add(req2);
		reqs.add(req3);
		reqs.add(req4);
		
		req5 = new Request();
		req5.setEmployee(george);
		req5.setId(1);
		reqs.add(req5);


		given(this.authService.findById("emp1")).willReturn(auth);
		given(this.employeeService.findByUsername("emp1")).willReturn(TEST_EMPLOYEE_ID);

    }
  

    @WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldGetRequests() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID))
			.andExpect(status().isOk()).andExpect(view().name("employees/requests"));
	}

	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldAcceptRequest() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/accept", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
			.andExpect(status().isOk()).andExpect(view().name("employees/requests"));
	}

	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldDeclineRequest() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/decline", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
			.andExpect(status().isOk()).andExpect(view().name("employees/requests"));
	}

	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldGetAppointments() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/appointments", TEST_EMPLOYEE_ID))
			.andExpect(status().isOk()).andExpect(view().name("employees/requests"));
	}

	

	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldNotGetRequests() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests", 2))
			.andExpect(status().isOk()).andExpect(view().name("exception"));
	}

	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldNotAcceptRequest() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/accept", 2, TEST_REQUEST_ID))
			.andExpect(status().isOk()).andExpect(view().name("exception"));
	}

	
	@WithMockUser(value = "emp1",  authorities = {"employee"}, username = "emp1")
    @Test
	void testShouldNotDeclineRequest() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/decline", 2, TEST_REQUEST_ID))
			.andExpect(status().isOk()).andExpect(view().name("exception"));
	}


}