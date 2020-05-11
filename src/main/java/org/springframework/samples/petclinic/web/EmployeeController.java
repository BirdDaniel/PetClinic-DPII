package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.ItemService;
import org.springframework.samples.petclinic.service.RequestService;
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
public class EmployeeController {

	private final RequestService requestService;
	private final EmployeeService employeeService;
	private final ClinicService clinicService;
	private final ResidenceService residenceService;
	private final ItemService itemService;
	private final static String VIEW_MY_REQUESTS = "employees/requests";
	private final static String VIEW_MY_APPOINTMENTS = "employees/appointments";
	private final static String CREATE_OR_UPDATE_ITEMLIST = "employees/createOrUpdateItemsList";
	

	@Autowired
	public EmployeeController(EmployeeService employeeService, RequestService requestService, ClinicService clinicService,
			ResidenceService residenceService, ItemService itemService) {
		
		this.requestService = requestService;
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

	@GetMapping("/requests")
	public String getRequestsEmployee(Employee employee, Map<String, Object> model) {

		if(isAuth(employee)){

			SortedSet<Request> res = new TreeSet<>(Comparator.comparing(Request::getRequestDate));
			Set<Request> requests = this.employeeService.getRequests(employee.getId());

			if(requests!=null)
				res.addAll(requests);

			model.put("loggedUser", employee.getId());
			model.put("requests", res);
			return VIEW_MY_REQUESTS;

		}

		return "redirect:/oups";

	}

	@GetMapping("/appointments")
	public String allAppointments(Employee employee, Map<String, Object> model) {

		if(isAuth(employee)){
		Collection<Request> appointments = this.requestService
												.findAcceptedByEmployeeId(employee.getId());
		
		if (appointments != null)
			model.put("appointments", appointments);

		model.put("loggedUser", employee.getId());
		return VIEW_MY_APPOINTMENTS;
	}

	return "redirect:/oups";

	}

	@GetMapping("/requests/{requestId}/accept")
	public String acceptRequest(Employee employee, 
								@PathVariable("requestId") Integer id,
								Map<String, Object> model) {

		if(isAuth(employee)){	

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);

			if (request != null) {
				request.setStatus(true);
				this.requestService.save(request);
			}
		
			return "redirect:/employees/{employeeId}/requests";
		}
		
		return "redirect:/oups";
		
	}

	@GetMapping("/{requestType}/{requestId}/decline")
	public String declineRequest(Employee employee, @PathVariable("requestId") int id,
			@PathVariable("requestType") String requestType, Map<String, Object> model) {

		if(isAuth(employee)){

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			if (request != null) {
				request.setStatus(false);
				this.requestService.save(request);
			}
			if (requestType.equals("requests")) {
				return "redirect:/employees/{employeeId}/requests";
			} else {
				return "redirect:/employees/{employeeId}/appointments";
			}
		}

		return "redirect:/oups";
		
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
		Item item = new Item();
		
		Clinic clinic = this.clinicService.findByEmployee(employee);
		Residence residence = this.residenceService.findByEmployee(employee);
		
		if(clinic!= null) {
			clinic.addItems(item);
			model.addAttribute("item", item);
		}else if(residence!= null) {
			residence.addItems(item);
			model.addAttribute("item", item);	
		}
		return CREATE_OR_UPDATE_ITEMLIST;
	}

	@PostMapping(value = "/itemsList/new")
	public String processCreationForm(Employee employee, @Valid Item item, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("item", item);
			return CREATE_OR_UPDATE_ITEMLIST;
		}
		else {
                    try{
                    	Clinic clinic = this.clinicService.findByEmployee(employee);
                		Residence residence = this.residenceService.findByEmployee(employee);
                		
                		
                		if(clinic!= null) {
                			this.itemService.saveItem(item, clinic);
                		}else if(residence!= null) {                			
                			this.itemService.saveItem(item, residence);
                		}
                    	
                    }catch(DuplicatedItemNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return CREATE_OR_UPDATE_ITEMLIST;
                    }
                    return "redirect:/employees/{employeeId}/itemsList";
		}
	}

}
