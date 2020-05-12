package org.springframework.samples.petclinic.bdd.e2e;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.ClinicController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ClinicControllerTestsE2E {
    
    private static final int TEST_CLINIC_ID = 1;

    @Autowired
    private ClinicController clinicController;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldGetAllClinics() throws Exception{
        mockMvc.perform(get("/clinic/findAll"))
        .andExpect(status().isOk())
        .andExpect(view().name("services/clinics"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldNotGetAllClinics() throws Exception{
        mockMvc.perform(get("/clinic/findAll"))
        .andExpect(status().is4xxClientError());
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldGetClinic() throws Exception{
        mockMvc.perform(get("/clinic/{clinicId}", TEST_CLINIC_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("services/clinicServiceDetails"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldNotGetClinic() throws Exception{
        mockMvc.perform(get("/clinic/{clinicId}", TEST_CLINIC_ID))
        .andExpect(status().is4xxClientError());
    }

}