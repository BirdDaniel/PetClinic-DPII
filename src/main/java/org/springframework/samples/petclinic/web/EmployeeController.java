package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees/{employeeId}")
public class EmployeeController extends SecurityController{

	private final RequestService requestService;
	private final static String VIEW_MY_REQUESTS = "employees/requests";
	private final static String VIEW_MY_APPOINTMENTS = "employees/appointments";

	@Autowired
	public EmployeeController(OwnerService ownerService, EmployeeService employeeService,
								AuthoritiesService authoritiesService, RequestService requestService) {
		super(ownerService, employeeService, authoritiesService);
		this.requestService = requestService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("employee")
	public Employee findEmployee(@PathVariable("employeeId") int employeeId){
		return this.employeeService.findEmployeeById(employeeId);
	}

	@GetMapping("/requests")
	public String RequestsEmployee(Employee employee, Map<String, Object> model){
		
		Integer loggedUserId = (Integer) model.get("loggedUser");
		System.out.println(loggedUserId);

		if(loggedUserId==employee.getId()){
			SortedSet<Request> res = new TreeSet<>(Comparator.comparing(Request::getRequestDate));
			Set<Request> requests = this.employeeService.getRequests(employee.getId());
			res.addAll(requests);
			model.put("requests", res);
			return VIEW_MY_REQUESTS;
		}
		return "redirect:/oups";
	}

	@GetMapping("/appointments")
	public String allAppointments(Employee employee, Map<String, Object> model){

		Integer loggedUserId = (Integer) model.get("loggedUser");
		if(loggedUserId==employee.getId()){
			Collection<Request> appointments = this.requestService.findAcceptedByEmployeeId(employee.getId());
			
			if(appointments!=null)
				model.put("appointments", appointments);

			return VIEW_MY_APPOINTMENTS;
		}

		return "redirect:/oups";
	}

	@GetMapping("/requests/{requestId}/accept")
	public String acceptRequest(Employee employee,@PathVariable("requestId") Integer id, Map<String,Object> model){
		
		Integer loggedEmployeeId = (Integer) model.get("loggedUser");
		if(employee.getId() == loggedEmployeeId){
			Request request = this.requestService.findById(id);
			if(request!=null){
				request.setStatus(true);
				this.requestService.save(request);
			}
		} else {
			return "redirect:/oups";
		}
		return "redirect:/employees/{employeeId}/requests";
	}

	@GetMapping("/requests/{requestId}/decline")
	public String declineRequest(Employee employee, @PathVariable("requestId") int id, Map<String,Object> model){
		
		// El modelo guarda en todo momento un atributo con el id del usuario logeado
		Integer loggedEmployeeId = (Integer) model.get("loggedUser");
		if(employee.getId() == loggedEmployeeId){
		
			Request request = this.requestService.findById(id);
			if(request!=null){
				request.setStatus(false);
				this.requestService.save(request);
		}
	
		return "redirect:/employees/{employeeId}/requests";

		} else {
			System.out.println("Han intentado cancelar una request sin la identificación necesaria");
			return "redirect:/oups";
		}	
	} 
	

}
