package org.springframework.samples.petclinic.bdd.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.web.ItemController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTests {
	
	private static final int TEST_ITEM_ID = 1;

	private static final int TEST_CLINIC_ID = 1;

	private static final int TEST_RESIDENCE_ID = 1;

	private static final int TEST_EMPLOYEE_ID = 1;
	
	
	@Autowired
	private ItemController itemController;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ItemService itemService;

	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ResidenceService residenceService;
	
//	// LISTADO
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldViewMyItemsList() throws Exception{
//
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.allItems(employee.getId(), model);
//			
//			assertEquals(view, "employees/itemsList");
//		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotViewMyItemsList() throws Exception{
//
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.allItems(employee.getId(), model);
//			
//			assertEquals(view, "redirect:/oups");
//		}
//		
//		//AÑADIR ITEMS
//		
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldAddItem() throws Exception{
////			mockMvc.perform(get("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID))
////			.andExpect(status().isOk())
////			.andExpect(model().attributeExists("item"))
////			.andExpect(view().name("employees/createOrUpdateItemsList"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.initCreationForm(employee, model);
//			
//			assertEquals(view, "employees/createOrUpdateItemsList");
//			
//			assertNotNull(model.get("item"));
//			
//		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotAddItem() throws Exception{
////			mockMvc.perform(get("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/oups"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.initCreationForm(employee, model);
//			
//			assertEquals(view, "redirect:/oups");
//			
//		}
//		
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldSaveNewItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
////					.param("Name", "Collares chicos")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "0.2")
////					.param("Stock", "3"))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			Item newItem = new Item();
//			newItem.setName("Collares chicos");
//			newItem.setDescription("prueba");
//			newItem.setPrice(6.0);
//			newItem.setSale(0.2);
//			newItem.setStock(3);
//			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//			
//			String view = itemController.processCreationForm(employee, newItem, bindingResult, model);
//			
//			assertEquals(view, "redirect:/employees/{employeeId}/itemsList");
//			
//			
//			
//		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotSaveNewItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
////					.param("Name", "Collares chicos")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "0.2")
////					.param("Stock", "3"))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/oups"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			ModelMap model = new ModelMap();
//			Item newItem = new Item();
//			newItem.setName("Collares chicos");
//			newItem.setDescription("prueba");
//			newItem.setPrice(6.0);
//			newItem.setSale(0.2);
//			newItem.setStock(3);
//			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//			
//			String view = itemController.processCreationForm(employee, newItem, bindingResult, model);
//			
//			assertEquals(view, "redirect:/oups");
//		}
//
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldNotSaveWrongValuesNewItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/new", TEST_EMPLOYEE_ID).with(csrf())
////					.param("Name", "Collares baratos")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "1.2")
////					.param("Stock", "-3"))
////			.andExpect(model().attributeHasErrors("item")).andExpect(status().isOk())
////			.andExpect(view().name("employees/createOrUpdateItemsList"));
//		Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//		ModelMap model = new ModelMap();
//		Item newItem = new Item();
//		newItem.setName("Collares chicos");
//		newItem.setDescription("prueba");
//		newItem.setPrice(6.0);
//		newItem.setSale(1.2);
//		newItem.setStock(-3);
//		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//		bindingResult.reject("sale", "Has Errors!");
//		bindingResult.reject("stock", "Has Errors!");
//		
//		String view = itemController.processCreationForm(employee, newItem, bindingResult, model);
//		
//		assertEquals(view, "employees/createOrUpdateItemsList");
//		}
//		
//		
//		
//		//EDITAR ITEM
//		
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldEditItem() throws Exception{
////			mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
////			.andExpect(status().isOk())
////			.andExpect(model().attributeExists("item"))
////			.andExpect(view().name("employees/createOrUpdateItemsList"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.initUpdateForm(item.getId(), employee, model);
//			
//			assertEquals(view, "employees/createOrUpdateItemsList");
//		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotEditItem() throws Exception{
////			mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/oups"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			String view = itemController.initUpdateForm(item.getId(), employee, model);
//			
//			assertEquals(view, "redirect:/oups");
//		}
//		
//		
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldSaveEditItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
////					.param("Name", "Collares pequeños")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "0.2")
////					.param("Stock", "3"))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			item.setName("Collares pequeños");
//			item.setDescription("prueba");
//			item.setPrice(6.0);
//			item.setSale(0.2);
//			item.setStock(3);
//			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//			
//			String view = itemController.processUpdateForm(item, bindingResult, employee, item.getId(), model);
//			
//			assertEquals(view, "redirect:/employees/{employeeId}/itemsList");
//			
//		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotSaveEditItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
////					.param("Name", "Collares pequeños")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "0.2")
////					.param("Stock", "3"))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/oups"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			item.setName("Collares pequeños");
//			item.setDescription("prueba");
//			item.setPrice(6.0);
//			item.setSale(0.2);
//			item.setStock(3);
//			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//			
//			String view = itemController.processUpdateForm(item, bindingResult, employee, item.getId(), model);
//			
//			assertEquals(view, "redirect:/oups");
//			
//		}
//		
//		@WithMockUser(username="emp1",authorities= {"employee"})
//		@Test
//		void shouldNotSaveWrongValuesEditItem() throws Exception{
////			mockMvc.perform(post("/employees/{employeeId}/itemsList/{itemId}/edit", TEST_EMPLOYEE_ID, TEST_ITEM_ID).with(csrf())
////					.param("Name", "Collares pequeños")
////					.param("Description", "prueba")
////					.param("Price", "6.0")
////					.param("Sale", "1.2")
////					.param("Stock", "-3"))
////			.andExpect(model().attributeHasErrors("item")).andExpect(status().isOk())
////			.andExpect(view().name("employees/createOrUpdateItemsList"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			item.setName("Collares chicos");
//			item.setDescription("prueba");
//			item.setPrice(6.0);
//			item.setSale(1.2);
//			item.setStock(-3);
//			BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
//			bindingResult.reject("sale", "Has Errors!");
//			bindingResult.reject("stock", "Has Errors!");
//			
//			String view = itemController.processUpdateForm(item, bindingResult, employee, item.getId(), model);
//			
//			assertEquals(view, "employees/createOrUpdateItemsList");
//		}
//		
		//DELETE ITEM
		
		@WithMockUser(username="emp1",authorities= {"employee"})
		@Test
		void shouldDeleteItem() throws Exception{
//			mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/delete", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(view().name("redirect:/employees/{employeeId}/itemsList"));
			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
			Item item = this.itemService.findItemById(TEST_ITEM_ID);
			ModelMap model = new ModelMap();
			
			String view = itemController.deleteItem(item.getId(), employee, model);
			
			assertEquals(view, "redirect:/employees/{employeeId}/itemsList");
		}
//		
//		@WithMockUser(username="emp2",authorities= {"employee"})
//		@Test
//		void shouldNotDeleteItem() throws Exception{
////			mockMvc.perform(get("/employees/{employeeId}/itemsList/{itemId}/delete", TEST_EMPLOYEE_ID, TEST_ITEM_ID))
////			.andExpect(status().is3xxRedirection())
////			.andExpect(view().name("redirect:/oups"));
//			Employee employee=employeeService.findEmployeeById(TEST_EMPLOYEE_ID);
//			Item item = this.itemService.findItemById(TEST_ITEM_ID);
//			ModelMap model = new ModelMap();
//			
//			String view = itemController.deleteItem(item.getId(), employee, model);
//			
//			assertEquals(view, "redirect:/oups");
//		}
//		
		

}
