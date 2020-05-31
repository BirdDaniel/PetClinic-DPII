package org.springframework.samples.petclinic.bdd.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class RequestControllerE2E {

    private static final int TEST_OWNER_ID = 1;
    private static final int TEST_SERVICE_ID = 1;
    private static final String TEST_SERVICE_NAME = "clinic";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService petService;
    
    @Autowired
    private RequestService requestService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ResidenceService residenceService;
    
    @Autowired
    private ClinicService clinicService;

    
    @WithMockUser(value = "owner1", authorities={"employee"})
    @Test
    void shouldGetRequestForm() throws Exception{
        mockMvc.perform(get("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("pets"))
        .andExpect(model().attributeExists("service"))
        .andExpect(model().attributeExists("request"))
        .andExpect(view().name("requests/createRequest"));
    }

    @WithMockUser(value = "emp1", authorities={"employee"})
    @Test
    void shouldNotGetRequestForm() throws Exception{
        mockMvc.perform(get("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID))
        .andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "owner1", authorities= {"owner"})
    @Test
    void shouldSaveCreatedRequest() throws Exception{
        mockMvc.perform(post("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID).with(csrf())
                        .param("serviceDate", "2021/08/15 12:00")
                        .param("requestDate", "2021/08/15 12:00")
                        .param("finishDate", "2021/08/15 12:00")
                        .param("pet", "pet")
        			)
        //.andExpect(status().is3xxRedirection())
        .andExpect(status().isOk())
		.andExpect(view().name("redirect:/owners/"+TEST_OWNER_ID+"/myRequestList"));
    }

    @WithMockUser(value = "owner1", authorities= {"owner"})
    @Test
    void shouldNotSaveCreatedRequest() throws Exception{
        mockMvc.perform(post("/createRequest/{serviceName}/{serviceId}", 
                        TEST_SERVICE_NAME, TEST_SERVICE_ID).with(csrf())
                        .param("serviceDate", "2020/08/15 12:00")
                        .param("pet","Mimi" )
                        )
        .andExpect(model().attributeExists("pets"))
        .andExpect(model().attributeExists("service"))
        .andExpect(model().attributeHasFieldErrors("request", "pet"))
        .andExpect(status().isOk())
		.andExpect(view().name("requests/createRequest"));
    }

}