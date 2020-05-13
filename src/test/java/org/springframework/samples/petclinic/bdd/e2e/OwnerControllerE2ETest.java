package org.springframework.samples.petclinic.bdd.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;

import javax.transaction.Transactional;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class OwnerControllerE2ETest {
	
private static final int TEST_OWNER_ID = 1;
	
	private static final int TEST_REQUEST_CLINIC_ID = 2;
	
	private static final int TEST_REQUEST_RESIDENCE_ID = 5;
	
	private static final int TEST_REQUEST_NULL_ID = 7;
	
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PetService petService;

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ResidenceService residenceService;
	
	@Autowired
	private MockMvc mockMvc;
	
	 //OWNER DETAILS
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldGetOwnerDetails() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/ownerDetails"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotGetOwnerDetails() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	//OWNER EDIT
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldEditOwnerDetails() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotEditOwnerDetails() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldSaveEditOwnerDetails() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(csrf())
				.param("firstName", "Pepe")
				.param("lastName", "mockito")
				.param("telephone", "6789625782")
				.param("address", "345 Maple St."))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldNotSaveEditOwnerDetails() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(csrf())
				.param("firstName", "")
				.param("lastName", "")
				.param("telephone", "678962iari")
				.param("address", ""))
		.andExpect(model().attributeHasErrors("owner"))
		.andExpect(model().attributeHasFieldErrors("owner", "firstName"))
		.andExpect(model().attributeHasFieldErrors("owner", "lastName"))
		.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
		.andExpect(model().attributeHasFieldErrors("owner", "address"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotSave2EditOwnerDetails() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(csrf())
				.param("firstName", "Pepe")
				.param("lastName", "mockito")
				.param("telephone", "6789625782")
				.param("address", "345 Maple St."))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	//OWNER MYREQUESTLIST
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewOwnerMyRequestList() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/myRequestList"));
	}

	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewOwnerMyRequestList() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList",  TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	//OWNER APPOINTMENTS
//	
//	@WithMockUser(value = "owner1")
//	@Test
//	void shouldViewOwnerAppointments() throws Exception{
//		mockMvc.perform(get("/owners/{ownerId}/appointments",  TEST_OWNER_ID))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeExists("requests"))
//		.andExpect(view().name("owners/appointments"));
//	}
	
	//OWNER PETS IN RESIDENCE

	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewPetsInResidence() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myPetList/residence",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("requests"))
		.andExpect(view().name("owners/myPetResidence"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewPetsInResidence() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myPetList/residence",  TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	

// OWNER PET LIST
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyPetList() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myPetList",  TEST_OWNER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("pets"))
		.andExpect(view().name("/owners/myPetList"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyPetList() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myPetList",  TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
        
	//OWNER MY REQUEST LIST / DETAILS
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyRequestListDetailsNULL() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_NULL_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsNULL() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_NULL_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	//CLINIC
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyRequestListDetailsCLINIC() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_CLINIC_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("clinic"))
		.andExpect(model().attribute("clinic", hasProperty("name", is("Clinica 1"))))
		.andExpect(model().attribute("clinic", hasProperty("address", is("Elm Street s/n"))))
		.andExpect(model().attribute("clinic", hasProperty("open", is(LocalTime.of(12, 0)))))
		.andExpect(model().attribute("clinic", hasProperty("rating", is("The rating is 3 stars"))))
		.andExpect(view().name("services/clinicServiceDetails"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsCLINIC() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_CLINIC_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeDoesNotExist("clinic"))
		.andExpect(view().name("redirect:/oups"));
	}
	
	//RESIDENCE
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyRequestListDetailsRESIDENCE() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_RESIDENCE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("residence"))
		.andExpect(model().attribute("residence", hasProperty("name", is("Residence \"Happy Pet\""))))
		.andExpect(model().attribute("residence", hasProperty("rating", is("The rating is 3 stars"))))
		.andExpect(model().attribute("residence", hasProperty("address", is("Madison Square, 51-B"))))
		.andExpect(model().attribute("residence", hasProperty("close", is(LocalTime.of(10, 10)))))
		.andExpect(view().name("services/residenceServiceDetails"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsRESIDENCE() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_RESIDENCE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeDoesNotExist("residence"))
		.andExpect(view().name("redirect:/oups"));
	}

}
