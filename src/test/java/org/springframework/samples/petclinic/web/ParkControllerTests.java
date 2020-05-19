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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Park;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ParkService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ParkController.class,
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfigurer.class),
            excludeAutoConfiguration= SecurityConfiguration.class)
public class ParkControllerTests {

    private static final int TEST_PARK_ID = 1;

    private Park park;
    
    private Owner owner;

    @Autowired
    private MockMvc mockMvc;

    
    @MockBean
    private ParkService parkService;

    @MockBean
    private OwnerService ownerService;

    @BeforeEach
    void setup() {
        
        owner = new Owner();
        owner.setId(1);
        park = new Park();
        List<Park> parks = new ArrayList<>();
        parks.add(park);
        park.setId(1);
        park.setOwner(owner);

        

        given(this.parkService.findAllParks()).willReturn(parks);
		given(this.ownerService.findIdByUsername("owner1")).willReturn(1);
		given(this.ownerService.findIdByUsername("owner2")).willReturn(2);
        given(this.ownerService.findOwnerById(1)).willReturn(owner);
        given(this.parkService.findById(1)).willReturn(park);
    }
    
    @WithMockUser(value = "owner1")
    @Test
    void shouldGetAllParks() throws Exception{
        mockMvc.perform(get("/parks"))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("parks"))
		.andExpect(view().name("parks/parkList"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldGetCreateParkPage() throws Exception{
        mockMvc.perform(get("/parks/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("park"))
		.andExpect(view().name("parks/newPark"));
    }

    
    @WithMockUser(value = "emp1")
    @Test
    void shouldNotGetCreateParkPage() throws Exception{
        mockMvc.perform(get("/parks/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    
    @WithMockUser(value = "owner1")
    @Test
    void shouldSaveCreatePark() throws Exception{
        mockMvc.perform(post("/parks/new").with(csrf())
        .param("name", "Parque de prueba")
        .param("address", "c/Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldNotSaveCreatePark() throws Exception{
        mockMvc.perform(post("/parks/new").with(csrf())
        .param("address", "c/Test")
        .param("name", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("parks/newPark"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldShowEditParkPage() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/edit", TEST_PARK_ID))
        .andExpect(status().isOk())
        .andExpect(model().attribute("park", park))
        .andExpect(view().name("parks/newPark"));
    }

    @WithMockUser(value = "owner2")
    @Test
    void shouldNotShowEditParkPage() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/edit", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldUpdatePark() throws Exception{
        mockMvc.perform(post("/parks/{parkId}/edit", TEST_PARK_ID).with(csrf()
        ).param("name", "parque de pruebas")
        .param("address", "C/ Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldNotUpdatePark() throws Exception{
        mockMvc.perform(post("/parks/{parkId}/edit", TEST_PARK_ID).with(csrf()
        ).param("name", "parque de pruebas"))
        .andExpect(status().isOk())
        .andExpect(view().name("parks/newPark"));
    }

    @WithMockUser(value = "owner2")
    @Test
    void shouldNotUpdatePark2() throws Exception{
        mockMvc.perform(post("/parks/{parkId}/edit", TEST_PARK_ID).with(csrf()
        ).param("name", "parque de pruebas")
        .param("address", "C/ Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

    @WithMockUser(value = "owner1")
    @Test
    void shouldDeletePark() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/delete", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/parks"));
    }

    @WithMockUser(value = "owner2")
    @Test
    void shouldNotDeletePark() throws Exception{
        mockMvc.perform(get("/parks/{parkId}/delete", TEST_PARK_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/oups"));
    }

}