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

import java.util.Collection;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final RequestService requestService;
	
	private final OwnerService ownerService;
	
	private final PetService petService;

	
	@Autowired
	public OwnerController(OwnerService ownerService,
			RequestService requestService, 
			PetService petService) {

			this.ownerService = ownerService;
			this.requestService = requestService;
			this.petService = petService;
	}

	private boolean isAuth(int ownerId) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer loggedId = this.ownerService.findIdByUsername(user.getUsername());
		return ownerId == loggedId; 
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	
	@GetMapping("/owners/{ownerId}")
	public String showOwner(@PathVariable("ownerId") int ownerId, Model model) {

		if(isAuth(ownerId)){

			Owner owner = this.ownerService.findOwnerById(ownerId);
			model.addAttribute(owner);
			model.addAttribute("loggedUser", ownerId);
			return "owners/ownerDetails";

		}

		return "redirect:/oups";
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initEditOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {

		if(isAuth(ownerId)){
			Owner owner = this.ownerService.findOwnerById(ownerId);
			model.addAttribute(owner);
			model.addAttribute("loggedUser", ownerId);
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		return "redirect:/oups";
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processEditOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId, Model model) {
		
		if(isAuth(ownerId)){
			if (result.hasErrors()) {
				model.addAttribute("loggedUser", ownerId);
				return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
			}
			else {
				owner.setId(ownerId);
				this.ownerService.saveOwner(owner);
				model.addAttribute("loggedUser", ownerId);

				return "redirect:/owners/{ownerId}";
			}
		}
		return "redirect:/oups";
	}

	
	
	/**Obtain a Request list of a Owner*/
	//Dani
	@GetMapping(value = "/owners/{ownerId}/myRequestList")
	public String requestListForm(@PathVariable("ownerId") int ownerId, Model model) {

		if(isAuth(ownerId)) {
			Owner owner = this.ownerService.findOwnerById(ownerId);
			model.addAttribute("owner", owner);
			model.addAttribute("loggedUser", ownerId);
			return "owners/myRequestList";
		}
		return "redirect:/oups";
	}
	
	/**Obtain a Request list of a Owner only accepted*/
	@GetMapping(value = "/owners/{ownerId}/appointments")
	public String appointmentsForm(@PathVariable("ownerId") int ownerId, Model model) {
		if(isAuth(ownerId)){
			Owner owner = this.ownerService.findOwnerById(ownerId);
			Set<Request> requests = owner.getAcceptedRequests();
			model.addAttribute("requests", requests);
			model.addAttribute("loggedUser", ownerId);
			return "owners/appointments";
		}

		return "redirect:/oups";

	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList/residence")
	public String requestPetResidence(@PathVariable("ownerId") int ownerId, Model model) {

		if(isAuth(ownerId)){
			Collection<Request> reqs = this.requestService.findAcceptedResByOwnerId(ownerId);
			model.addAttribute("requests", reqs);
			return "owners/myPetResidence";
		}
		return "redirect:/oups";
	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList")
	public String processFindForm2(@PathVariable("ownerId") int ownerId, Pet pet,  BindingResult result, Model model) {
		if(isAuth(ownerId)){
			Owner owner = this.ownerService.findOwnerById(ownerId);
			if (pet.getName() == null) {
				model.addAttribute("pets", owner.getPets()); 
			}else if(pet.getName().equals("")){
				model.addAttribute("pets", owner.getPets());
			}else {
				Collection<Pet> results = this.petService.findPetsOfOwnerByName(ownerId, pet.getName());
				if (results.isEmpty()) {
					result.rejectValue("name", "notFound", "not found");
				}
				else {
					model.addAttribute("pets" , results);
				}
			}
				return "/owners/myPetList";
			}

			return "redirect:/oups";

		}

}

