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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/residence")
public class ResidenceController{

	private final ResidenceService residenceService;
	private final OwnerService ownerService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public ResidenceController(ResidenceService residenceService, OwnerService ownerService) {

		this.residenceService = residenceService;
		this.ownerService = ownerService;
	}

	private Integer isAuth(){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer ownerId = this.ownerService.findOwnerByUsername(user.getUsername()).getId();

		return ownerId==null ? 0:ownerId;

	}

	@GetMapping(value = "/findAll")
	public String residences(ModelMap model) {
		Integer ownerId = isAuth();
		if(ownerId!=0){
			Iterable<Residence> residences= this.residenceService.findAll();
			model.addAttribute("loggedUser", ownerId);
			model.addAttribute("residences", residences);
			return "services/residences";
		}
		return "redirect:/oups";
	}


	@GetMapping("/{residenceId}")
	public String showResidence(@PathVariable("residenceId") int residenceId,Model model) {
		
		Integer ownerId = isAuth();
		if(ownerId!=0){
			Residence residence=this.residenceService.findResidenceById(residenceId);
			model.addAttribute("loggedUser", ownerId);
			model.addAttribute("residence",residence);
			return "services/residenceServiceDetails";
		}
		return "redirect:/oups";
	}

	

}
