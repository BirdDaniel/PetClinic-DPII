package org.springframework.samples.petclinic.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SecurityController {

    OwnerService ownerService;
	EmployeeService employeeService;
    AuthoritiesService authoritiesService;
    
    @Autowired
    public SecurityController(OwnerService ownerService, EmployeeService employeeService, AuthoritiesService authoritiesService){
        this.authoritiesService = authoritiesService;
        this.ownerService = ownerService;
        this.employeeService = employeeService;
    }

    @ModelAttribute("loggedUser")
    public Integer loggedUser(Principal principal){

        if(principal!=null) {
            
            String username = principal.getName();
            String role = this.authoritiesService.findById(username).getAuthority();
			
                if(role.equals("owner")){
                    System.out.println("Role es Owner");
                    Integer loggedOwner = this.ownerService.findIdByUsername(username);
                    return loggedOwner;

                }else if(role.equals("employee")){

                    Integer loggedEmployee = this.employeeService.findByUsername(username);
                    return loggedEmployee; 
                
                }else {
                    return null;
                }

            } else {
                return null;
            }

    }

}