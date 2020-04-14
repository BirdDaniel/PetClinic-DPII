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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.OwnerService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/clinic")
public class ClinicController{

	private final ClinicService clinicService;

	@Autowired
	public ClinicController(ClinicService clinicService) {
					this.clinicService = clinicService;
					
	}

	@GetMapping(value = "/findAll")
	public String clinics(Model model) {
		Iterable<Clinic> clinics= this.clinicService.findAll();
		model.addAttribute("clinics", clinics);
		return "services/clinics";
	}

	@GetMapping("/{clinicId}")
	public String showClinic(@PathVariable("clinicId") int clinicId,Model model) {
		
		Clinic clinic=this.clinicService.findClinicById(clinicId);
		model.addAttribute("clinic",clinic);
		return "services/clinicServiceDetails";
	}
	


}
