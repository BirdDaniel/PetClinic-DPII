package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedItemNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees/{employeeId}")
public class ItemController {

	private final EmployeeService employeeService;
	private final ClinicService clinicService;
	private final ResidenceService residenceService;
	private final ItemService itemService;

	private final static String CREATE_OR_UPDATE_ITEMLIST = "employees/createOrUpdateItemsList";
	

	@Autowired
	public ItemController(EmployeeService employeeService, ClinicService clinicService,
			ResidenceService residenceService, ItemService itemService) {
		
		this.employeeService = employeeService;
		this.clinicService = clinicService;
		this.residenceService = residenceService;
		this.itemService = itemService;

	}

	private boolean isAuth(Employee employee){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer empId = this.employeeService.findByUsername(user.getUsername());
		return employee.getId()==empId;

	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("employee")
	public Employee findEmployee(@PathVariable("employeeId") int employeeId) {
		return this.employeeService.findEmployeeById(employeeId);
	}
	
	@GetMapping("/itemsList")
	public String allItems(@PathVariable("employeeId") int employeeId, Model model) {

		Employee employee = this.employeeService.findEmployeeById(employeeId);
		

		if (!isAuth(employee)) {
			return "redirect:/oups";
		}
			
			Clinic clinic = this.clinicService.findByEmployee(employee);
			Residence residence = this.residenceService.findByEmployee(employee);
			
			if(clinic!= null) {
				model.addAttribute("items", clinic.getItems());
				return "employees/itemsList";
			}else if(residence!= null) {
				model.addAttribute("items", residence.getItems());
				return "employees/itemsList";
				
			}else {
				return "redirect:/employees/{employeeId}";
			}
		}
	
	@GetMapping(value = "/itemsList/new")
	public String initCreationForm(Employee employee, ModelMap model) {
		
		if (!isAuth(employee)) {
			return "redirect:/oups";
		}
		Item item = new Item();
		
		Clinic clinic = this.clinicService.findByEmployee(employee);
		Residence residence = this.residenceService.findByEmployee(employee);
		
		if(clinic!= null) {
			clinic.addItems(item);
			model.put("item", item);
		}else if(residence!= null) {
			residence.addItems(item);
			model.put("item", item);	
		}
		return CREATE_OR_UPDATE_ITEMLIST;
	}

	@PostMapping(value = "/itemsList/new")
	public String processCreationForm(Employee employee, @Valid Item item, BindingResult result, ModelMap model) {	
		if (!isAuth(employee)) {
			return "redirect:/oups";
		}
		if (result.hasErrors()) {
			model.put("item", item);
			return CREATE_OR_UPDATE_ITEMLIST;
		}
		else {
			try{
				Clinic clinic = this.clinicService.findByEmployee(employee);
				Residence residence = this.residenceService.findByEmployee(employee);
					if(clinic!= null) {
						clinic.addItems(item);
                		this.itemService.saveItem(item);
                	}else if(residence!= null) {
                		residence.addItems(item);
                		this.itemService.saveItem(item);
                	}
				}catch(DuplicatedItemNameException ex){
					result.rejectValue("name", "duplicate", "already exists");
					return CREATE_OR_UPDATE_ITEMLIST;
				}
			return "redirect:/employees/{employeeId}/itemsList";
		}
	}
	
	@GetMapping(value = "/itemsList/{itemId}/edit")
	public String initUpdateForm(@PathVariable("itemId") int itemId, Employee employee, ModelMap model) {
		
		if (!isAuth(employee)) {
			return "redirect:/oups";
		}
		Item item = this.itemService.findItemById(itemId);
		model.put("item", item);
		return CREATE_OR_UPDATE_ITEMLIST;
	}

    
        @PostMapping(value = "/itemsList/{itemId}/edit")
	public String processUpdateForm(@Valid Item item, BindingResult result, Employee employee, @PathVariable("itemId") int itemId, ModelMap model) {
        	if (!isAuth(employee)) {
    			return "redirect:/oups";
    		}
		if (result.hasErrors()) {
			model.put("item", item);
			return CREATE_OR_UPDATE_ITEMLIST;
		}
		else {
			Item itemToUpdate=this.itemService.findItemById(itemId);
			BeanUtils.copyProperties(item, itemToUpdate, "clinic", "residence", "id");
			try {
				Clinic clinic = this.clinicService.findByEmployee(employee);
                Residence residence = this.residenceService.findByEmployee(employee);
	            if(clinic!= null) {
                	this.itemService.saveItem(itemToUpdate);
                }else if(residence!= null) {                			
                	this.itemService.saveItem(itemToUpdate);
                }
            }catch (DuplicatedItemNameException ex) {
            	result.rejectValue("name", "duplicate", "already exists");
            	return CREATE_OR_UPDATE_ITEMLIST;
            }
			return "redirect:/employees/{employeeId}/itemsList";
		}
	}
        
    @GetMapping(value = "/itemsList/{itemId}/delete")
    public String deletePet(@PathVariable("itemId") int itemId, Employee employee, ModelMap model) {
    	
    	if (!isAuth(employee)) {
			return "redirect:/oups";
		}
    	
    	Item item = this.itemService.findItemById(itemId);
    	
    	Clinic clinic = this.clinicService.findByEmployee(employee);
    	
        Residence residence = this.residenceService.findByEmployee(employee);
        
        
     
        if(clinic!= null) {
        	clinic.removeItems(item);
        }else if(residence!= null) {                			
        	residence.removeItems(item);
        }
    	if(item!=null){
    		this.itemService.deleteItem(item);
    	}
    	return "redirect:/employees/{employeeId}/itemsList";
    }
}
