package org.springframework.samples.petclinic.bdd.e2e;

/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.PetController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class PetControllerE2ETests {

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 1;

	@Autowired
	private PetController petController;


	@Autowired
	private PetService petService;
        
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "owner1", authorities= {"owner"})
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
		.andExpect(status().isOk())		
		.andExpect(view().name("pets/createOrUpdatePetForm")).andExpect(model().attributeExists("pet"));
	}
	
	@WithMockUser(value = "owner2", authorities= {"owner"})
    @Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
		.andExpect(status().is3xxRedirection())		
		.andExpect(view().name("redirect:/oups"));
	}

	@WithMockUser(value = "owner1", authorities= {"owner"})
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("type", "cat")
							.param("birthDate", "2015/02/12"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@WithMockUser(value = "owner2", authorities= {"owner"})
    @Test
	void testNotProcessCreationForm() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
				.with(csrf())
				.param("name", "Betty")
				.param("type", "cat")
				.param("birthDate", "2015/02/12"))
		.andExpect(status().is3xxRedirection())		
		.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(value = "owner1", authorities= {"owner"})
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@WithMockUser(value = "owner1", authorities= {"owner"})
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("pet"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}
	
	@WithMockUser(value = "owner2", authorities= {"owner"})
	@Test
	void testNotInitUpdateForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/oups"));
	}
    
	@WithMockUser(value = "owner1", authorities= {"owner"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("type", "cat")
							.param("birthDate", "2015/02/12"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}/myPetList"));
	}
	
	@WithMockUser(value = "owner2", authorities= {"owner"})
	@Test
	void testNotProcessUpdateForm() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
				.with(csrf())
				.param("name", "Betty")
				.param("type", "hamster")
				.param("birthDate", "2015/02/12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/oups"));
	}
    
    
	@WithMockUser(value = "owner1", authorities= {"owner"})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
							.with(csrf())
							.param("name", "Betty")
							.param("birthDate", "2015/02/12"))
				.andExpect(model().attributeHasNoErrors("owner"))
				.andExpect(model().attributeHasErrors("pet")).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}
	
	@WithMockUser(value = "owner1", authorities= {"owner"})
	@Test
	void shouldDeletePet() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/delete", TEST_OWNER_ID, TEST_PET_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}/myPetList"));
	}
	
	@WithMockUser(value = "owner2", authorities= {"owner"})
	@Test
	void shouldNotDeletePet() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/delete",  TEST_OWNER_ID, TEST_PET_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

}
