package org.springframework.samples.petclinic.bdd.e2e;

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
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ItemControllerE2ETest {
	
private static final int TEST_ITEM_ID = 1;
	
	private static final int TEST_CLINIC_ID = 1;
	
	private static final int TEST_RESIDENCE_ID = 1;
	
	private static final int TEST_EMPLOYEE_ID = 1;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ResidenceService residenceService;
	
	@Autowired
	private MockMvc mockMvc;
	
	// LISTADO
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldViewMyItemsList() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList",  TEST_EMPLOYEE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("employees/itemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotViewMyItemsList() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList",  TEST_EMPLOYEE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	//AÑADIR ITEMS
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldAddItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("item"))
		.andExpect(view().name("employees/createOrUpdateItemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotAddItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldSaveNewItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
				.param("Name", "Collares chicos")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "0.2")
				.param("Stock", "3"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotSaveNewItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
				.param("Name", "Collares chicos")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "0.2")
				.param("Stock", "3"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}

	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldNotSaveWrongValuesNewItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
				.param("Name", "Collares baratos")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "1.2")
				.param("Stock", "-3"))
		.andExpect(model().attributeHasErrors("item"))
		.andExpect(model().attributeHasFieldErrors("item", "stock"))
		.andExpect(model().attributeHasFieldErrors("item", "sale"))
		.andExpect(status().isOk())
		.andExpect(view().name("employees/createOrUpdateItemsList"));
	}
	
	
	
	//EDITAR ITEM
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldEditItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("item"))
		.andExpect(view().name("employees/createOrUpdateItemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotEditItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldSaveEditItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
				.param("Name", "Collares pequeños")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "0.2")
				.param("Stock", "3"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotSaveEditItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
				.param("Name", "Collares pequeños")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "0.2")
				.param("Stock", "3"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldNotSaveWrongValuesEditItem() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
				.param("Name", "Collares pequeños")
				.param("Description", "prueba")
				.param("Price", "6.0")
				.param("Sale", "1.2")
				.param("Stock", "-3"))
			.andExpect(model().attributeHasErrors("item"))
			.andExpect(model().attributeHasFieldErrors("item", "stock"))
			.andExpect(model().attributeHasFieldErrors("item", "sale"))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/createOrUpdateItemsList"));
	}
	
	//DELETE ITEM
	
	@WithMockUser(username="emp1",authorities= {"employee"})
	@Test
	void shouldDeleteItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/delete", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
	}
	
	@WithMockUser(username="emp2",authorities= {"employee"})
	@Test
	void shouldNotDeleteItem() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/delete", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/oups"));
	}
	
	


}
