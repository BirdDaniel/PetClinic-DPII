package org.springframework.samples.petclinic.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WelcomeController{
	
	OwnerService ownerService;
	EmployeeService employeeService;
	AuthoritiesService authoritiesService;

	@Autowired
	public WelcomeController(AuthoritiesService authoritiesService,OwnerService ownerService, EmployeeService employeeService) {
		this.ownerService= ownerService;
		this.employeeService= employeeService;
		this.authoritiesService = authoritiesService;
	}
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Principal principal,Map<String, Object> model) {

		
		if(principal!= null){  //Compruebo si se está loggeado

			//Compruebo con qué rol están loggeados
			String rol = this.authoritiesService.findById(principal.getName()).getAuthority();
			Integer loggedId=null;

			//Si el rol es owner, lo busco con ownerService,
			//Si es employee, lo busco en employeeService
			if(rol.equals("owner")){

				loggedId = this.ownerService.findIdByUsername(principal.getName());

			} else if(rol.equals("employee")){

				loggedId = this.employeeService.findByUsername(principal.getName());

			}
			//Sea el rol que sea lo meto en el modelo
			model.put("loggedUser", loggedId);

		}
	    return "welcome";
	}


}
