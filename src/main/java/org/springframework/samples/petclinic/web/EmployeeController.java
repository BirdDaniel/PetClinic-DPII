package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedItemNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees/{employeeId}")
public class EmployeeController {

	private final RequestService requestService;
	private final EmployeeService employeeService;
	private final ClinicService clinicService;
	private final ResidenceService residenceService;
	private final static String VIEW_MY_REQUESTS = "employees/requests";
	private final static String VIEW_MY_APPOINTMENTS = "employees/appointments";
	

	

	@Autowired
	public EmployeeController(EmployeeService employeeService, RequestService requestService,
			ClinicService clinicService,ResidenceService residenceService) {
		
		this.requestService = requestService;
		this.employeeService = employeeService;
		this.clinicService=clinicService;
		this.residenceService=residenceService;

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
	public String RequestsEmployee(Employee employee, Map<String, Object> model) {

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
	@GetMapping("/colleagues")
	public String ColleaguesEmployee(Employee employee,
			Map<String, Object> model) {
		if(isAuth(employee)  &&(this.clinicService.findByEmployee(employee)!=null )) {
			Clinic clinic= this.clinicService.findByEmployee(employee);
			Collection<Employee> colleagues= this.employeeService.findEmployeeByClinicId(clinic.getId());
		colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("loggedUser", employee.getId());
			return "employees/colleagues";
		}else if(isAuth(employee)  &&(this.residenceService.findByEmployee(employee)!=null )) {
			Residence residence= this.residenceService.findByEmployee(employee);
			Collection<Employee> colleagues= this.employeeService.findEmployeeByResidenceId(residence.getId());
			colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("loggedUser", employee.getId());
			return "employees/colleagues";
		}
		return "redirect:/oups";
	}
	@GetMapping("/{requestType}/{requestId}/assign")
	public String assignRequest(Employee employee, @PathVariable("requestId") int id,
			@PathVariable("requestType") String requestType, Map<String, Object> model) {
		
		if(isAuth(employee)&&(this.clinicService.findByEmployee(employee)!=null )){

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			Clinic clinic=this.clinicService.findByEmployee(employee);
			Collection<Employee> colleagues=this.employeeService.findEmployeeByClinicId(clinic.getId());
			model.put("colleagues", colleagues);
			model.put("request", request);
			model.put("assign", true);
			return "employees/colleagues";
		}else if(isAuth(employee)  &&(this.residenceService.findByEmployee(employee)!=null )){
			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			Residence residence=this.residenceService.findByEmployee(employee);
			Collection<Employee> colleagues=this.employeeService.findEmployeeByClinicId(residence.getId());
			model.put("colleagues", colleagues);
			model.put("request", request);
			model.put("assign", true);
			return "employees/colleagues";	
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
	@GetMapping("/{requestType}/{requestId}/{colleagueId}/reassign")
	public String reassignRequest(Employee employee, @PathVariable("requestId") int id,
			@PathVariable("requestType") String requestType
			,@PathVariable("colleagueId") int colleagueId,Map<String, Object> model) {

		if(isAuth(employee)){
		//	if(model.get("assign").equals(true)) {
				model.put("loggedUser", employee.getId());
				Request request = this.requestService.findById(id);
				Employee colleague= this.employeeService.findEmployeeById(colleagueId);
				model.put("assign", false);
				if (request != null) {
				
					request.setEmployee(colleague);
					this.requestService.save(request);
					
					
				}
				
					return "redirect:/employees/{employeeId}/requests";
				}
			//}

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
	


}
