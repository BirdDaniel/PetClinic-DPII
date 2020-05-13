package org.springframework.samples.petclinic.bdd.e2e;
import javax.transaction.Transactional;

import org.springframework.samples.petclinic.service.ResidenceService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ResidenceControllerE2E {
	  private static final int TEST_EMPLOYEE_ID = 1;
	  private static final int TEST_RESIDENCE_ID = 1;
	    @Autowired
	    private ResidenceService ResidenceService;
	    @Autowired
	    private MockMvc mockMvc;
	    @WithMockUser(value = "owner1", authorities = {"owner"})
	    @Test
	    void shouldFindAllResidences() throws Exception{
	        mockMvc.perform(get("/residence/findAll"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("services/residences"));
	    }
	    @WithMockUser(value = "emp1", authorities = {"employee"})
	    @Test
	    void shouldNotFindAllParks() throws Exception{
	        mockMvc.perform(get("/residences"))
	        .andExpect(status().is4xxClientError());
	    }
	    @WithMockUser(value = "owner1", authorities = {"owner"})
	    @Test
	    void shouldGetClinic() throws Exception{
	        mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID))
	        .andExpect(status().isOk())
	        .andExpect(view().name("services/residenceServiceDetails"));
	    }

	    @WithMockUser(value = "emp1", authorities = {"employee"})
	    @Test
	    void shouldNotGetClinic() throws Exception{
	        mockMvc.perform(get("/residence/{residenceId}", TEST_RESIDENCE_ID))
	        .andExpect(status().is4xxClientError());
	    }
}
