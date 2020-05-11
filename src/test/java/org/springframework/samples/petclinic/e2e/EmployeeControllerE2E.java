package org.springframework.samples.petclinic.e2e;

import javax.transaction.Transactional;

import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ItemService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private static final int TEST_REQUEST_ID = 1;
    
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
        .andExpect(view().name("employees/requests"));
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