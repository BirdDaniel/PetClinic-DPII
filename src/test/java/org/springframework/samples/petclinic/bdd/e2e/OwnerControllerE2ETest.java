package org.springframework.samples.petclinic.bdd.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;
import java.util.Calendar;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
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
	
	private static final int TEST_CLINIC_ID = 2;
	
	private static final int TEST_RESIDENCE_ID = 2;
	
	
//	@Autowired
//	private OwnerController ownerController;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private PetService petService;

	@MockBean
	private RequestService requestService;
	
	@MockBean
	private ClinicService clinicService;
	
	@MockBean
	private ResidenceService residenceService;
	
	@Autowired
	private MockMvc mockMvc;

	private Owner david;
	
	@MockBean
	private Model model;
		
	private Request reqEmpty;
	
	private Request reqClinic;
	
	private Request reqResidence;
	
	private Clinic clinic;
	
	private Residence residence;
	

	@BeforeEach
	void setup(){

		david = new Owner();
		david.setId(TEST_OWNER_ID);
		david.setFirstName("firstName");
		david.setLastName("LastName");
		david.setAddress("addressDavid");
		david.setTelephone("645789456");
		
		reqEmpty = new Request();
		reqEmpty.setId(TEST_REQUEST_NULL_ID);
		Calendar c = Calendar.getInstance();
		c.set(2019, 8, 5, 19, 00);
		//reqEmpty.setRequestDate(c.getTime());
		c.set(2030, 8, 8, 17, 00);
		//reqEmpty.setServiceDate(c.getTime());
		reqEmpty.setStatus(true);
		
		reqClinic = new Request();
		reqClinic.setId(TEST_REQUEST_CLINIC_ID);
		c.set(2019, 8, 1, 18, 00);
		//reqClinic.setRequestDate(c.getTime());
		c.set(2030, 8, 6, 19, 00);
		//reqClinic.setServiceDate(c.getTime());
		reqClinic.setStatus(true);
		
		reqResidence = new Request();
		reqResidence.setId(TEST_REQUEST_RESIDENCE_ID);
		c.set(2019, 8, 1, 15, 30);
		//reqResidence.setRequestDate(c.getTime());
		c.set(2030, 8, 6, 14, 00);
		//reqResidence.setServiceDate(c.getTime());
		reqResidence.setStatus(true);
		
		clinic = new Clinic();
		clinic.setId(TEST_CLINIC_ID);
		clinic.setName("Clinica 1");
		clinic.setAddress("Elm Street s/n");
		clinic.setOpen(LocalTime.of(10, 10));
		clinic.setClose(LocalTime.of(12, 00));
		clinic.setRating(3);
		clinic.setDescription("Description 1");
		clinic.setMax(10);
		clinic.setPrice(2.5);
		
		residence = new Residence();
		residence.setId(TEST_RESIDENCE_ID);
		residence.setName("Residencia 1");
		residence.setAddress("Madison Square, 51-B");
		residence.setOpen(LocalTime.of(10, 10));
		residence.setClose(LocalTime.of(12, 00));
		residence.setRating(3);
		residence.setDescription("Description 1");
		residence.setMax(10);
		residence.setPrice(2.5);
		
		given(this.ownerService.findIdByUsername("owner1")).willReturn(1);
		given(this.ownerService.findIdByUsername("owner2")).willReturn(2);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(david);

	}
	
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
		given(this.requestService.findById(TEST_REQUEST_NULL_ID)).willReturn(reqEmpty, new Request());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_NULL_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsNULL() throws Exception{
		given(this.requestService.findById(TEST_REQUEST_NULL_ID)).willReturn(reqEmpty, new Request());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_NULL_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	//CLINIC
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyRequestListDetailsCLINIC() throws Exception{
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_CLINIC_ID))).willReturn(clinic, new Clinic());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_CLINIC_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("clinic"))
		.andExpect(model().attribute("clinic", hasProperty("name", is(clinic.getName()))))
		.andExpect(model().attribute("clinic", hasProperty("address", is(clinic.getAddress()))))
		.andExpect(model().attribute("clinic", hasProperty("open", is(clinic.getOpen()))))
		.andExpect(model().attribute("clinic", hasProperty("rating", is(clinic.getRating()))))
		.andExpect(view().name("services/clinicServiceDetails"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsCLINIC() throws Exception{
		given(this.clinicService.findClinicByRequest(this.requestService.findById(TEST_REQUEST_CLINIC_ID))).willReturn(clinic, new Clinic());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_CLINIC_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeDoesNotExist("clinic"))
		.andExpect(view().name("redirect:/oups"));
	}
	
	//RESIDENCE
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void shouldViewMyRequestListDetailsRESIDENCE() throws Exception{
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_RESIDENCE_ID))).willReturn(residence, new Residence());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_RESIDENCE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("residence"))
		.andExpect(model().attribute("residence", hasProperty("name", is(residence.getName()))))
		.andExpect(model().attribute("residence", hasProperty("rating", is(residence.getRating()))))
		.andExpect(model().attribute("residence", hasProperty("address", is(residence.getAddress()))))
		.andExpect(model().attribute("residence", hasProperty("close", is(residence.getClose()))))
		.andExpect(view().name("services/residenceServiceDetails"));
	}
	
	@WithMockUser(username="owner2",authorities= {"owner"})
	@Test
	void shouldNotViewMyRequestListDetailsRESIDENCE() throws Exception{
		given(this.residenceService.findResidenceByRequest(this.requestService.findById(TEST_REQUEST_RESIDENCE_ID))).willReturn(residence, new Residence());
		mockMvc.perform(get("/owners/{ownerId}/myRequestList/{requestId}/details", TEST_OWNER_ID, TEST_REQUEST_RESIDENCE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeDoesNotExist("residence"))
		.andExpect(view().name("redirect:/oups"));
	}

}
