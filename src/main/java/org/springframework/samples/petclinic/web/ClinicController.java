/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/clinic")
public class ClinicController{

	private final ClinicService clinicService;
	private final OwnerService ownerService;

	@Autowired
	public ClinicController(ClinicService clinicService, OwnerService ownerService) {
		
		this.clinicService = clinicService;
		this.ownerService = ownerService;
					
	}

	private Integer isAuth(){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer ownerId = this.ownerService.findIdByUsername(user.getUsername());

		return ownerId==null ? 0:ownerId;

	}

	@GetMapping(value = "/findAll")
	public String clinics(Model model) {

		Integer ownerId = isAuth();
		if(ownerId!=0){
			Iterable<Clinic> clinics= this.clinicService.findAll();
			model.addAttribute("loggedUser", ownerId);
			model.addAttribute("clinics", clinics);
			return "services/clinics";
		}
		return "redirect:/oups";
	}

	@GetMapping("/{clinicId}")
	public String showClinic(@PathVariable("clinicId") int clinicId,Model model) {
		
		Integer ownerId = isAuth();
		if(ownerId!=0){
		Clinic clinic=this.clinicService.findClinicById(clinicId);
		if(clinic!=null) {
		model.addAttribute("loggedUser", ownerId);
		model.addAttribute("clinic",clinic);
		return "services/clinicServiceDetails";
		}
		}
		return "redirect:/oups";
	
	}
	


}
