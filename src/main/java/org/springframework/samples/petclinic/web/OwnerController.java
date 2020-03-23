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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	
	private final OwnerService ownerService;
	
	private final RequestService requestService;
	
	private final ClinicService clinicService;

	private final ResidenceService residenceService;
	
	private final PetService petService;

	
	@Autowired
	public OwnerController(OwnerService ownerService, 
			ClinicService clinicService, 
			RequestService requestService, 
			ResidenceService residenceService,
			PetService petService,
			UserService userService, AuthoritiesService authoritiesService) {
		this.ownerService = ownerService;
		this.requestService = requestService;
		this.clinicService = clinicService;
		this.residenceService = residenceService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.ownerService.saveOwner(owner);
			
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping(value = "/owners/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwners";
	}

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}
		else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			owner.setId(ownerId);
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.ownerService.findOwnerById(ownerId));
		return mav;
	}
	
	/**Obtain a Request list of a Owner*/
	@GetMapping(value = "/owners/{ownerId}/myRequestList")
	public String requestListForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Set<Request> requests = owner.getRequests();
		model.addAttribute("requests", requests);
		return "owners/myRequestList";
	}
	
	/**Obtain a Request list of a Owner only accepted*/
	@GetMapping(value = "/owners/{ownerId}/myRequestAccepted")
	public String requestAcceptedForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Set<Request> requests = owner.getAcceptedRequests();
		model.addAttribute("requests", requests);
		return "owners/myRequestList";
	}
	
	/**Obtain a Service of a Owner*/
	@GetMapping(value = "/owners/{ownerId}/myRequestList/{requestId}/details")
	public String servicesForm(@PathVariable("requestId") int requestId, Model model,Boolean requestD) {
		
		Request req = this.requestService.findById(requestId);
		
		if(this.clinicService.findClinicByRequest(req)!= null) {
			Clinic clinic = this.clinicService.findClinicByRequest(req);
			model.addAttribute("clinic", clinic);
			//requestD=true;
			return "services/clinicServiceDetails";
		}else if(this.residenceService.findResidenceByRequest(req)!= null) {
			Residence residence = this.residenceService.findResidenceByRequest(req);
			model.addAttribute("residence", residence);
			//requestD=false;
			return "services/residenceServiceDetails";
		}else {
			model.addAttribute(req.getOwner());
			return "redirect:/owners/" + req.getOwner().getId();
		}
	}
	
	@GetMapping(value = "/owners/{ownerId}/myPetList/residence")
	public String requestPetResidence(@PathVariable("ownerId") int ownerId, Model model) {
		List <Request>  reqs = new ArrayList<>(this.requestService.findAcceptedByOwnerId(ownerId));
		int i = reqs.size()-1;
		//Usamos while porque al usar un buble for nos salta ConcurrentModificationException que suele
		//pasar al borrar/modificar una lista de la base de datos.
		while(i>=0) {
			if(this.residenceService.findResidenceByRequest(reqs.get(i))== null) {
				reqs.remove(reqs.get(i));
			}
			i--;			
		}
			model.addAttribute("requests", reqs);
			return "owners/myPetResidence";
	}
	

	@GetMapping(value = "/owners/{ownerId}/myPetList")
	public String processFindForm2(@PathVariable("ownerId") int ownerId, Pet pet,  BindingResult result, Model model) {
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

		
}

