package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebMvcTest(controllers = RequestController.class,
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfigurer.class),
            excludeAutoConfiguration= SecurityConfiguration.class)
public class RequestControllerTests {

    private static final int TEST_REQUEST_ID = 1;
    private static final int TEST_OWNER_ID = 1;
    private static final int TEST_SERVICE_ID = 1;
    private static final String TEST_SERVICE_NAME = "clinic";

    private Request request;

    private Pet pet;
    
    private Owner owner;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private ResidenceService residenceService;
    
    @MockBean
    private ClinicService clinicService;

    @BeforeEach
    void setup() {
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Jose luis");

        Set<Employee> employees = new HashSet<>();
        employees.add(employee);

        Residence residence = new Residence();
        residence.setId(1);
        residence.setEmployees(employees);

        pet = new Pet();
        pet.setId(1);
        pet.setName("Mimi");

        owner = new Owner();
        owner.setId(1);
        owner.addPet(pet);
        
        given(this.ownerService.findOwnerByUsername("owner1")).willReturn(owner);
		given(this.ownerService.findIdByUsername("owner1")).willReturn(1);
		given(this.ownerService.findIdByUsername("owner2")).willReturn(2);
        given(this.ownerService.findOwnerById(1)).willReturn(owner);
        given(this.residenceService.findResidenceById(1)).willReturn(residence);
        
    }
    
    @WithMockUser(value = "owner1")
    @Test
    void shouldGetRequestForm() throws Exception{
        mockMvc.perform(get("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID))
        .andExpect(status().isOk())
		.andExpect(view().name("requests/createRequest"));
    }

    @WithMockUser(value = "emp1")
    @Test
    void shouldNotGetRequestForm() throws Exception{
        mockMvc.perform(get("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID))
        .andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldSaveCreatedRequest() throws Exception{
        mockMvc.perform(post("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID).with(csrf())
                        .param("serviceDate", "2020/08/15 12:00")
                        // .param("pet", )
                        )
        .andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+TEST_OWNER_ID+"/myRequestList"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldNotSaveCreatedRequest() throws Exception{
        mockMvc.perform(post("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID).with(csrf())
                        .param("serviceDate", "2020/08/15 12:00")
                        .param("pet","Mimi" )
                        )
        .andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+TEST_OWNER_ID+"/myRequestList"));
    }

}