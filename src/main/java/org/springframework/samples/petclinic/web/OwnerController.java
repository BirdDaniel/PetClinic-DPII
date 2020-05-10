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
import org.springframework.samples.petclinic.model.Employee;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	private final ClinicService clinicService;
	
	private final ResidenceService residenceService;


	
	@Autowired
	public OwnerController(OwnerService ownerService,
			RequestService requestService, 
			PetService petService,
			ClinicService clinicService,
			ResidenceService residenceService) {

			this.ownerService = ownerService;
			this.requestService = requestService;
			this.petService = petService;
			this.clinicService = clinicService;
			this.residenceService = residenceService;
	}

	private boolean isAuth(int ownerId) {
		System.out.println(ownerId);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(user);
		Integer loggedId = this.ownerService.findIdByUsername(user.getUsername());
		System.out.println(loggedId);
		return ownerId == loggedId; 
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}

	
	@GetMapping("/owners/{ownerId}")
	public String showOwner(Owner owner, ModelMap model) {

		System.out.println(owner);
		if(isAuth(owner.getId())){

			model.addAttribute(owner);
			model.addAttribute("loggedUser", owner.getId());
			return "owners/ownerDetails";

		}

		return "redirect:/oups";
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initEditOwnerForm(@PathVariable("ownerId") int ownerId, ModelMap model) {

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
			@PathVariable("ownerId") int ownerId, ModelMap model) {
		
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
	public String requestListForm(@PathVariable("ownerId") int ownerId, ModelMap model) {

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
	public String appointmentsForm(@PathVariable("ownerId") int ownerId, ModelMap model) {
		if(isAuth(ownerId)){
			Collection<Request> requests = this.requestService.findAcceptedByOwnerId(ownerId);
			model.addAttribute("requests", requests);
			model.addAttribute("loggedUser", ownerId);
			return "owners/appointments";
		}

		return "redirect:/oups";

	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList/residence")
	public String requestPetResidence(@PathVariable("ownerId") int ownerId, ModelMap model) {

		if(isAuth(ownerId)){
			Collection<Request> reqs = this.requestService.findAcceptedResByOwnerId(ownerId);
			model.addAttribute("requests", reqs);
			model.addAttribute("loggedUser", ownerId);
			return "owners/myPetResidence";
		}
		return "redirect:/oups";
	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList")
	public String processFindForm2(@PathVariable("ownerId") int ownerId, Pet pet,  BindingResult result, ModelMap model) {
		if(isAuth(ownerId)){
			Owner owner = this.ownerService.findOwnerById(ownerId);
			if (pet.getName() == null) {
				model.addAttribute("pets", owner.getPets()); 
				model.addAttribute("loggedUser", ownerId);
			}else if(pet.getName().equals("")){
				model.addAttribute("pets", owner.getPets());
				model.addAttribute("loggedUser", ownerId);
			}else {
				Collection<Pet> results = this.petService.findPetsOfOwnerByName(ownerId, pet.getName());
				if (results.isEmpty()) {
					result.rejectValue("name", "notFound", "not found");
				}
				else {
					model.addAttribute("pets" , results);
					model.addAttribute("loggedUser", ownerId);
				}
			}
				return "/owners/myPetList";
			}

			return "redirect:/oups";

		}
	
	@GetMapping(value = "/owners/{ownerId}/myRequestList/{requestId}/details")
	public String servicesForm(@PathVariable("requestId") int requestId,
								@PathVariable("ownerId") int ownerId, ModelMap model) {
		if(isAuth(ownerId)){
			Request req = this.requestService.findById(requestId);
			model.addAttribute("loggedUser", ownerId);

			Clinic clinic = this.clinicService.findClinicByRequest(req);
			Residence residence = this.residenceService.findResidenceByRequest(req);
			
			if(clinic!= null) {
				model.addAttribute("clinic", clinic);
				return "services/clinicServiceDetails";
			}else if(residence!= null) {
				model.addAttribute("residence", residence);
				return "services/residenceServiceDetails";
			}else {
				return "redirect:/owners/{ownerId}";
			}
		}
			return "redirect:/oups";

	}

}

