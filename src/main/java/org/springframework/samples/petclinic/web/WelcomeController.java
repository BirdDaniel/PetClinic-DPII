package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class WelcomeController {
	
	OwnerService ownerService;
	EmployeeService employeeService;

	@Autowired
	public WelcomeController(OwnerService ownerService, EmployeeService employeeService) {
        this.ownerService = ownerService;
        this.employeeService= employeeService;
	}
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Principal principal, Map<String, Object> model) {	    
		if(principal!=null){
			String username = principal.getName();
			Owner loggedUser = this.ownerService.findByUsername(username);
			model.put("user", loggedUser);
			System.out.println("Logged as: "+ loggedUser.getFirstName() + " " + loggedUser.getLastName());
		}
	    return "welcome";
	  }


}
