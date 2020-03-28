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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class OwnerController extends SecurityController{

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final RequestService requestService;
	
	private final ClinicService clinicService;

	private final ResidenceService residenceService;
	
	private final PetService petService;

	
	@Autowired
	public OwnerController(OwnerService ownerService,
			EmployeeService employeeService,
			AuthoritiesService authoritiesService, 
			ClinicService clinicService, 
			RequestService requestService, 
			ResidenceService residenceService,
			PetService petService,
			UserService userService) {
		
			super(ownerService, employeeService, authoritiesService);
			this.requestService = requestService;
			this.clinicService = clinicService;
			this.residenceService = residenceService;
			this.petService = petService;
			
	}
	
	public static String validationUser(String direction, Integer id, Model model) {
			Integer loggedUserId = (Integer) model.getAttribute("loggedUser");

			if(loggedUserId!=id){
			return "redirect:/oups";
		}
		return direction;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}


	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {

		Owner owner = this.ownerService.findOwnerById(ownerId);
		Integer loggedOwner = (Integer) model.getAttribute("loggedUser");
		if(owner.getId()==loggedOwner){

			model.addAttribute(owner);
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		return "redirect:/oups";
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
	public String showOwner(@PathVariable("ownerId") int ownerId, Model model) {

		Owner owner = this.ownerService.findOwnerById(ownerId);
		Integer loggedOwner = (Integer) model.getAttribute("loggedUser");		

		if(owner.getId()==loggedOwner){
			model.addAttribute(owner);
			return "owners/ownerDetails";
		}
		return "redirect:/oups";
	}
	
	/**Obtain a Request list of a Owner*/
	//Dani
	@GetMapping(value = "/owners/{ownerId}/myRequestList")
	public String requestListForm(@PathVariable("ownerId") int ownerId, Model model) {

		Owner owner = this.ownerService.findOwnerById(ownerId);

		Integer loggedOwner = (Integer) model.getAttribute("loggedUser");

		if(owner.getId()==loggedOwner){
			Set<Request> requests = owner.getRequests();
			model.addAttribute("owner", owner);
			model.addAttribute("requests", requests);
			return "owners/myRequestList";
		}
		return "redirect:/oups";
	}
	
	/**Obtain a Request list of a Owner only accepted*/
	@GetMapping(value = "/owners/{ownerId}/appointments")
	public String requestAcceptedForm(@PathVariable("ownerId") int ownerId, Model model) {

		Owner owner = this.ownerService.findOwnerById(ownerId);
		Integer loggedOwner = (Integer) model.getAttribute("loggedUser");
		
		if(loggedOwner==owner.getId()){
			Set<Request> requests = owner.getAcceptedRequests();
			model.addAttribute("requests", requests);
			return "owners/appointments";
		}

		return "redirect:/oups";

	}
	
	/**Obtain a Service of a Owner*/
	/**Obtain a Service of a Owner*/
	//Dani
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
			return "redirect:/owners/{ownerId}";
		}
	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList/residence")
	public String requestPetResidence(@PathVariable("ownerId") int ownerId, Model model) {
		Collection<Request> reqs = this.requestService.findAcceptedResByOwnerId(ownerId);
		model.addAttribute("requests", reqs);
		return OwnerController.validationUser("owners/myPetResidence", ownerId, model);
	}
	
	//Grupo Dani y Josan
	@GetMapping(value = "/owners/{ownerId}/myPetList")
	public String processFindForm2(@PathVariable("ownerId") int ownerId, Pet pet,  BindingResult result, Model model) {

		System.out.println(pet);

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

