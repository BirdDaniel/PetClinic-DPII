package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
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
	AuthoritiesService authoritiesService;

	@Autowired
	public WelcomeController(OwnerService ownerService, EmployeeService employeeService, AuthoritiesService authoritiesService) {
        this.ownerService = ownerService;
		this.employeeService= employeeService;
		this.authoritiesService = authoritiesService;
	}
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Principal principal, Map<String, Object> model) {	    
		if(principal!=null){
			
			String username = principal.getName();
			Authorities user = this.authoritiesService.findById(username);
			String role = user.getAuthority();
			
			if(role.equals("owner")){
				
				Owner loggedUser = this.ownerService.findByUsername(username);
				model.put("user", loggedUser);

			}else if(role.equals("employee")){

				Employee loggedUser = this.employeeService.findByUsername(username);
				model.put("user", loggedUser);
				
			}

//			System.out.println("Logged as: "+ loggedUser.getFirstName() + " " + loggedUser.getLastName());
		}
	    return "welcome";
	  }


}
