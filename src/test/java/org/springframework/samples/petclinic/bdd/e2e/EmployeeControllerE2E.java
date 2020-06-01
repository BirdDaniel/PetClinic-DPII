package org.springframework.samples.petclinic.bdd.e2e;

import javax.transaction.Transactional;

import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ItemService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.EmployeeController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerE2E {
    
    private static final int TEST_EMPLOYEE_ID = 1;
    private static final int TEST_EMPLOYEE_ID4=4;
    private static final int TEST_EMPLOYEE_ID5=5;
    private static final int TEST_REQUEST_ID = 1;
    private static final int TEST_REQUEST_ID2 = 2;
    
    @Autowired
    private EmployeeController parkController;

    @Autowired
    private ResidenceService ResidenceService;

    @Autowired
    private ClinicService ClinicService;

    @Autowired
    private RequestService RequestService;

    @Autowired
    private ItemService ItemService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldGetRequests() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("requests"))
		.andExpect(model().attributeExists("loggedUser"))
        .andExpect(view().name("employees/requests"));
    }
    //Colleagues siempre muestra la vista, si no tiene se muestra vacia.
    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldGetColleagues() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/colleagues", TEST_EMPLOYEE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("colleagues"))
		.andExpect(model().attributeExists("loggedUser"))
        .andExpect(view().name("employees/colleagues"));
    }
    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldGetPets() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/pets", TEST_EMPLOYEE_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("employees/pets"));
    }
	@WithMockUser(value="emp5", authorities = {"employee"})
	@Test
	void shouldNotGetColleagues() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/colleagues", TEST_EMPLOYEE_ID))
		.andExpect(status().is3xxRedirection()) //Empty page
		.andExpect(view().name("redirect:/oups"));
	}

    @WithMockUser(value = "emp2", authorities = {"employee"})
    @Test
    void shouldNotGetRequests() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/requests", TEST_EMPLOYEE_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }
    

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldGetAppointments() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/appointments", TEST_EMPLOYEE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("loggedUser"))
        .andExpect(view().name("employees/appointments"));
    }

    @WithMockUser(value = "emp2", authorities = {"employee"})
    @Test
    void shouldNotGetAppointments() throws Exception{
        mockMvc.perform(get("/employees/{employeeId}/appointments", TEST_EMPLOYEE_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldAcceptRequest() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/requests/{requestId}/accept",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/employees/{employeeId}/requests"));
    }

    @WithMockUser(value = "emp2", authorities = {"employee"})
    @Test
    void shouldNotAcceptRequest() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/requests/{requestId}/accept",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldDeclineRequest() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/requests/{requestId}/decline",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/employees/{employeeId}/requests"));
    }
//    //ASSIGN
//    @WithMockUser(value = "emp1", authorities = {"employee"})
//    @Test
//    void shouldAssignRequest() throws Exception{
//        mockMvc.perform(get(
//                "/employees/{employeeId}/{requestType}/{requestId}/{colleagueId}/assign",
//                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID,TEST_REQUEST_ID2,TEST_COLLEAGUE_ID5))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(view().name("redirect:/employees/{employeeId}/requests"));
//    }
//    //Reasign
//    @WithMockUser(value = "emp1", authorities = {"employee"})
//    @Test
//    void shouldReassignRequest() throws Exception{
//        mockMvc.perform(get(
//                "/employees/{employeeId}/{requestType}/{requestId}/{colleagueId}/reassign",
//                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID,TEST_REQUEST_ID2,TEST_COLLEAGUE_ID5))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(view().name("redirect:/employees/{employeeId}/requests"));
//    }
//    
	@WithMockUser(value = "emp1",authorities = {"employee"})
	@Test
	void shouldGetAssignColleagues() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/assign", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("colleagues"))
		.andExpect(model().attributeExists("loggedUser"))
		.andExpect(model().attributeExists("request"))
		.andExpect(model().attributeExists("assign"))
		.andExpect(view().name("employees/colleagues"));
	}
	@WithMockUser(value="emp5",authorities = {"employee"})
	@Test
	void shouldNotGetAssignColleagues() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/assign", TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
		.andExpect(status().is3xxRedirection()) //Empty page
		.andExpect(view().name("redirect:/oups"));
	}
	@WithMockUser(value = "emp1",authorities = {"employee"})
	@Test
	void shouldGetReassignColleagues() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/{colleagueId}/reassign", TEST_EMPLOYEE_ID, TEST_REQUEST_ID,TEST_EMPLOYEE_ID5))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/requests"));
	}
	@WithMockUser(value="emp5",authorities = {"employee"})
	@Test
	void shouldNotGetReassignColleagues() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/requests/{requestId}/{colleagueId}/reassign", TEST_EMPLOYEE_ID, TEST_REQUEST_ID,TEST_EMPLOYEE_ID4))
		.andExpect(status().is3xxRedirection()) //Empty page
		.andExpect(view().name("redirect:/oups"));
	}

    @WithMockUser(value = "emp2", authorities = {"employee"})
    @Test
    void shouldNotDeclineRequest() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/requests/{requestId}/decline",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldCancelAppointment() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/appointments/{requestId}/decline",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/employees/{employeeId}/appointments"));
    }

    @WithMockUser(value = "emp2", authorities = {"employee"})
    @Test
    void shouldNotCancelAppointment() throws Exception{
        mockMvc.perform(get(
                "/employees/{employeeId}/appointments/{requestId}/decline",
                            TEST_EMPLOYEE_ID, TEST_REQUEST_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

}