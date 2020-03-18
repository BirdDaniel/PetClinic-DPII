package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{employeeId}")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService, UserService userService, AuthoritiesService authoritiesService) {
		this.employeeService = employeeService;
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
		Set<Request> requests = this.employeeService.getRequests(employee.getId());
		model.put("requests", requests);
		return "employees/requests";
	}

}
