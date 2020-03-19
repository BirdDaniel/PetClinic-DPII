package org.springframework.samples.petclinic.web;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees/{employeeId}")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final RequestService requestService;
	private final static String VIEW_MY_REQUESTS = "employees/requests";
	private final static String VIEW_MY_APPOINTMENTS = "employees/appointments";

	@Autowired
	public EmployeeController(EmployeeService employeeService, RequestService requestService) {
		this.employeeService = employeeService;
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
	public String myRequests(Employee employee, Map<String, Object> model){
		SortedSet<Request> res = new TreeSet<>(Comparator.comparing(Request::getDate));
		Set<Request> requests = this.employeeService.getRequests(employee.getId());
		res.addAll(requests);
		model.put("requests", res);
		return VIEW_MY_REQUESTS;
	}

	@GetMapping("/appointments")
	public String allAppointments(Employee employee, Map<String, Object> model){
		Set<Request> appointments = this.employeeService.getRequests(employee.getId());
		SortedSet<Request> res = new TreeSet<>(Comparator.comparing(Request::getDate));
		for(Request req : appointments){
			if(req.getStatus()!=null)
				if(req.getStatus()==true) 
						res.add(req);
		}
		if(res!=null)
			model.put("appointments", res);

		return VIEW_MY_APPOINTMENTS;
	}

	@GetMapping("/requests/{requestId}/accept")
	public String acceptRequest(Employee employee,@PathVariable("requestId") Integer id){
		System.out.println(employee);
		System.out.println("El id es: "+id);
		Request request = this.requestService.findById(id);
		if(request!=null){
			request.setStatus(true);
			this.requestService.save(request);
		}
		return "redirect:/employees/{employeeId}/requests";
	}

	@GetMapping("/requests/{requestId}/decline")
	public String declineRequest(Employee employee, @PathVariable("requestId") int id, Map<String,Object> model){
		Request request = this.requestService.findById(id);
		if(request!=null){
			request.setStatus(false);
			this.requestService.save(request);
		}
		return "redirect:/employees/{employeeId}/requests";
	}

}
