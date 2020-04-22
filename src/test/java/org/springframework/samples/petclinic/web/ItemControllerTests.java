package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

@WebMvcTest(controllers=OwnerController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ItemControllerTests {
	
	
	private static final int TEST_ITEM_ID = 1;
	
	private static final int TEST_CLINIC_ID = 2;
	
	private static final int TEST_RESIDENCE_ID = 2;
	
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
	
	private Employee employee;
	
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
		
		given(this.itemService.findItemById(TEST_ITEM_ID)).willReturn(item);
		
		
	}
	

}
