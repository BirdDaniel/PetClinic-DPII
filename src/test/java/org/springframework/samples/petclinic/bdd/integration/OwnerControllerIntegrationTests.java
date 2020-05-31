package org.springframework.samples.petclinic.bdd.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.web.OwnerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerIntegrationTests {
	
private static final int TEST_REQUEST_CLINIC_ID = 2;
	
	private static final int TEST_REQUEST_RESIDENCE_ID = 5;
	
	private static final int TEST_REQUEST_NULL_ID = 9;
	
	private static final int TEST_CLINIC_ID = 2;
	
	private static final int TEST_RESIDENCE_ID = 2;
	
	private static final int TEST_OWNER_ID = 1;
	
	
	@Autowired
	private OwnerController ownerController;
	
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
	
	 //OWNER DETAILS
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldGetOwnerDetails() throws Exception{
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			
			ModelMap model = new ModelMap();
			
			String view = ownerController.showOwner(owner, model);
			
			assertEquals(view, "owners/ownerDetails");
			
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotGetOwnerDetails() throws Exception{

			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			
			ModelMap model = new ModelMap();
			
			String view = ownerController.showOwner(owner, model);
			
			assertEquals(view, "redirect:/oups");
		}
		
		//OWNER EDIT
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldEditOwnerDetails() throws Exception{

			ModelMap model = new ModelMap();
			
			String view = ownerController.initEditOwnerForm(TEST_OWNER_ID, model);
			
			assertEquals(view, "owners/createOrUpdateOwnerForm");
			
			assertNotNull(model.get("owner"));
			
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotEditOwnerDetails() throws Exception{

			ModelMap model = new ModelMap();
			
			String view = ownerController.initEditOwnerForm(TEST_OWNER_ID, model);
			
			assertEquals(view, "redirect:/oups");
						
		}
		

		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldSaveEditOwnerDetails() throws Exception{

			ModelMap model = new ModelMap();
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			owner.setFirstName("Pepe");
			owner.setLastName("mockito");
			owner.setTelephone("6789625782");
			owner.setAddress("345 Maple St.");
			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
			
			String view = ownerController.processEditOwnerForm(owner, bindingResult, TEST_OWNER_ID, model);
			
			assertEquals(view, "redirect:/owners/{ownerId}");
		}
		
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldNotSaveEditOwnerDetails() throws Exception{
			
			ModelMap model = new ModelMap();
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			owner.setFirstName("");
			owner.setLastName("");
			owner.setTelephone("6789625782hgyu");
			owner.setAddress("");
			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
			bindingResult.reject("firstName", "Must not be empty");
			bindingResult.reject("lastName", "Must not be empty");
			bindingResult.reject("telephone", "Has Errors!");
			bindingResult.reject("address", "Must not be empty");
			
			String view = ownerController.processEditOwnerForm(owner, bindingResult, TEST_OWNER_ID, model);

			assertEquals(view, "owners/createOrUpdateOwnerForm");
			
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotSave2EditOwnerDetails() throws Exception{

			ModelMap model = new ModelMap();
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			owner.setFirstName("Pepe");
			owner.setLastName("mockito");
			owner.setTelephone("6789625782");
			owner.setAddress("345 Maple St.");
			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
			
			String view = ownerController.processEditOwnerForm(owner, bindingResult, TEST_OWNER_ID, model);
			
			assertEquals(view, "redirect:/oups");
		}
		
		//OWNER MYREQUESTLIST
		
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewOwnerMyRequestList() throws Exception{
			
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			String view = ownerController.requestListForm(owner.getId(), model);
			
			assertEquals(view, "owners/myRequestList");
		}

		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewOwnerMyRequestList() throws Exception{

			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			String view = ownerController.requestListForm(owner.getId(), model);
			
			assertEquals(view, "redirect:/oups");
		}
//		
//		//OWNER APPOINTMENTS
	//	
//		@WithMockUser(value = "owner1")
//		@Test
//		void shouldViewOwnerAppointments() throws Exception{
//			mockMvc.perform(get("/owners/{ownerId}/appointments",  TEST_OWNER_ID))
//			.andExpect(status().isOk())
//			.andExpect(model().attributeExists("requests"))
//			.andExpect(view().name("owners/appointments"));
//		}
		
		//OWNER PETS IN RESIDENCE

		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewPetsInResidence() throws Exception{

			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			String view = ownerController.requestPetResidence(owner.getId(), model);
			
			assertEquals(view, "owners/myPetResidence");
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewPetsInResidence() throws Exception{

			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			String view = ownerController.requestPetResidence(owner.getId(), model);
			
			assertEquals(view, "redirect:/oups");
			
		}
		

	// OWNER PET LIST
		
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewMyPetList() throws Exception{
			
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
			
			String view = ownerController.processFindForm2(owner.getId(), owner.getPet("Leo"), bindingResult, model);
			
			assertEquals(view, "/owners/myPetList");
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewMyPetList() throws Exception{

			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
			
			String view = ownerController.processFindForm2(owner.getId(), owner.getPet("Leo"), bindingResult, model);
			
			assertEquals(view, "redirect:/oups");
		}
	        
		//OWNER MY REQUEST LIST / DETAILS
		
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewMyRequestListDetailsNULL() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_NULL_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "redirect:/owners/{ownerId}");
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewMyRequestListDetailsNULL() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_NULL_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "redirect:/oups");
		}

		//CLINIC
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewMyRequestListDetailsCLINIC() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_CLINIC_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "services/clinicServiceDetails");
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewMyRequestListDetailsCLINIC() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_CLINIC_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "redirect:/oups");
			
		}
		
		//RESIDENCE
		
		@WithMockUser(username="owner1",authorities= {"owner"})
		@Test
		void shouldViewMyRequestListDetailsRESIDENCE() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_RESIDENCE_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "services/residenceServiceDetails");
		}
		
		@WithMockUser(username="owner2",authorities= {"owner"})
		@Test
		void shouldNotViewMyRequestListDetailsRESIDENCE() throws Exception{

			Request request = this.requestService.findById(TEST_REQUEST_RESIDENCE_ID);
			Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
			ModelMap model = new ModelMap();
			
			String view = ownerController.servicesForm(request.getId(), owner.getId(), model);
			
			assertEquals(view, "redirect:/oups");
		}
//

}
