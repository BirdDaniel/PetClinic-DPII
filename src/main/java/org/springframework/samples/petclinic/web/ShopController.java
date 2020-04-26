package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.ShopService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees/{employeeId}")
public class ShopController {

	private final ShopService shopService;
	
	private final EmployeeService employeeService;
	
	@Autowired
	public ShopController(ShopService shopService,
			EmployeeService employeeService) {

			this.shopService = shopService;
			this.employeeService = employeeService;
	}
	
	
	private boolean isAuth(Employee employee){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer empId = this.employeeService.findByUsername(user.getUsername());
		return employee.getId()==empId;

	}

}
