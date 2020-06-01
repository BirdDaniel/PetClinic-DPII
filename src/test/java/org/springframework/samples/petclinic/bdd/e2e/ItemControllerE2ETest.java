package org.springframework.samples.petclinic.bdd.e2e;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

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
	
	@MockBean
	private ItemService itemService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private ClinicService clinicService;
	
	@MockBean
	private ResidenceService residenceService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private Model model;
	
	private Item item;
	
	private Item item1;
	
	private Item item2;
	
	private Clinic clinic;
	
	private Residence residence;
	
	@BeforeEach
	void setup(){

		item = new Item();
		item.setId(TEST_ITEM_ID);
		item.setName("Collar");
		item.setDescription("Description 1");
		item.setPrice(15.);
		item.setSale(0.3);
		item.setStock(3);
		
		item1 = new Item();
		item1.setId(2);
		item1.setName("Collar1");
		item1.setDescription("Description 3");
		item1.setPrice(15.);
		item1.setSale(0.3);
		item1.setStock(3);
		
		item2 = new Item();
		item2.setId(3);
		item2.setName("Collar2");
		item2.setDescription("Description 2");
		item2.setPrice(15.);
		item2.setSale(0.3);
		item2.setStock(3);
		
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
		
		Set<Item> items = new HashSet<Item>();
		items.add(item);
		clinic.setItems(items);
		
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
		
		Employee george = new Employee();
		george.setId(TEST_EMPLOYEE_ID);
		
		Employee mikel = new Employee();
		mikel.setId(2);

		
		given(this.employeeService.findByUsername("emp1")).willReturn(1);
		given(this.employeeService.findByUsername("emp2")).willReturn(2);
		given(this.itemService.findItemById(TEST_ITEM_ID)).willReturn(item);
		given(this.itemService.findItemById(2)).willReturn(item1);
		given(this.itemService.findItemById(3)).willReturn(item2);
		given(this.employeeService.findEmployeeById(TEST_EMPLOYEE_ID)).willReturn(george);
		given(this.clinicService.findByEmployee(george)).willReturn(clinic);
		given(this.residenceService.findByEmployee(mikel)).willReturn(residence);
		
	}
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
		.andExpect(model().attributeHasErrors("item")).andExpect(status().isOk())
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
		.andExpect(model().attributeHasErrors("item")).andExpect(status().isOk())
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
