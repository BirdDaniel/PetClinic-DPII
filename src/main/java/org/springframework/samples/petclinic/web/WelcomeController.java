package org.springframework.samples.petclinic.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WelcomeController extends SecurityController{
	
	OwnerService ownerService;
	EmployeeService employeeService;
	AuthoritiesService authoritiesService;

	@Autowired
	public WelcomeController(OwnerService ownerService, EmployeeService employeeService, AuthoritiesService authoritiesService) {

       super(ownerService, employeeService, authoritiesService);

	}
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
	    return "welcome";
	}


}
