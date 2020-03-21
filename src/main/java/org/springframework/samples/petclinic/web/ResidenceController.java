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
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.Map;

import org.springframework.samples.petclinic.service.ResidenceService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/residence")
public class ResidenceController {

	private final ResidenceService residenceService;

	@Autowired
	public ResidenceController(ResidenceService residenceService) {
		this.residenceService = residenceService;
	}

	@GetMapping(value = "/findAll")
	public String residences(ModelMap model) {
		Iterable<Residence> residences= this.residenceService.findAll();
		model.addAttribute("residences", residences);
		return "services/residences";
	}

}
