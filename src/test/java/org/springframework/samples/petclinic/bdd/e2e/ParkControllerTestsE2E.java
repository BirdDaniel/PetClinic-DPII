package org.springframework.samples.petclinic.bdd.e2e;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ParkService;
import org.springframework.samples.petclinic.web.ParkController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ParkControllerTestsE2E {

    private static final int TEST_PARK_ID = 1;
    
    @Autowired
    private ParkController parkController;

    @Autowired
    private ParkService parkService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldFindAllParks() throws Exception{
        mockMvc.perform(get("/parks"))
        .andExpect(status().isOk())
        .andExpect(view().name("parks/parkList"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldNotFindAllParks() throws Exception{
        mockMvc.perform(get("/parks"))
        .andExpect(status().is4xxClientError());
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldGetCreateParkPage() throws Exception{
        mockMvc.perform(get("/parks/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("parks/newPark"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldNotGetCreateParkPage() throws Exception{
        mockMvc.perform(get("/parks/new"))
        .andExpect(status().is4xxClientError());
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldSaveCreatedPark() throws Exception{
        mockMvc.perform(post("/parks/new").with(csrf())
        .param("name", "Parque de Prueba")
        .param("address", "C/ Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldGetEditParkPage() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/edit", TEST_PARK_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("parks/newPark"));
    }

    @WithMockUser(value = "owner2", authorities = {"owner"})
    @Test
    void shouldNotGetEditParkPage() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/edit", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldSaveUpdatedPark() throws Exception{
        mockMvc.perform(post("/parks/{parkId}/edit", TEST_PARK_ID).with(csrf())
        .param("name", "Parque de la rueda")
        .param("address", "C/ Testing"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "emp1", authorities = {"employee"})
    @Test
    void shouldNotSaveUpdatedPark() throws Exception{
        mockMvc.perform(post("/parks/{parkId}/edit", TEST_PARK_ID).with(csrf())
        .param("name", "Parque de la rueda")
        .param("address", "C/ Testing"))
        .andExpect(status().is4xxClientError());
    }

    @WithMockUser(value = "owner1", authorities = {"owner"})
    @Test
    void shouldDeletePark() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/delete", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "owner2", authorities = {"owner"})
    @Test
    void shouldNotDeletePark() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/delete", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

}