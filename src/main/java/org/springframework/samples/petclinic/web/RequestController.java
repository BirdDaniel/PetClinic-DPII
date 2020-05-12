package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.samples.petclinic.service.exceptions.RequestWithoutPetException;
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

@Controller
public class RequestController {

    private final static String VIEW_CREATE_REQUEST = "requests/createRequest";
    private final RequestService requestService;
    private final ClinicService clinicService;
    private final ResidenceService residenceService;
    private final OwnerService ownerService;

    private Owner LoggedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Owner loggedUser = this.ownerService.findOwnerByUsername(user.getUsername());
		return loggedUser; 
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
    public RequestController(RequestService requestService,
                            ClinicService clinicService, 
                            ResidenceService residenceService,
                            OwnerService ownerService) {

        this.ownerService = ownerService;
        this.residenceService = residenceService;
        this.clinicService = clinicService;
		this.requestService = requestService;

    }
    
    @GetMapping("/createRequest/{serviceName}/{serviceId}")
    public String createRequestForm(@PathVariable("serviceName") String serviceName, 
                                    @PathVariable("serviceId") int serviceId, Model model){
            
        //COMPROBAR QUE SE ESTÁ LOGUEADO
        Owner userLogged = LoggedUser();

        if(userLogged!=null){

            //CREAR UNA REQUEST Y 
            Request request = new Request();
            model.addAttribute("loggedUser", userLogged.getId());
            
            request.setOwner(userLogged);

            List<Pet> pets = userLogged.getPets();
            model.addAttribute("pets", pets);

            //Coger el SERVICE DESDE EL QUE SE HACE
            if(serviceName.equals("residence")){
                Residence residence = this.residenceService.findResidenceById(serviceId); 
                
                model.addAttribute("request", request);
                model.addAttribute("service", "residence");
                model.addAttribute("residence", residence);
                
            }else if(serviceName.equals("clinic")) {                
                Clinic clinic = this.clinicService.findClinicById(serviceId);

                model.addAttribute("request", request);
                model.addAttribute("service", "clinic");
                model.addAttribute("residence", clinic);
            }
            return VIEW_CREATE_REQUEST;
        }
        return "redirect:/oups";
    }
    
    @PostMapping("/createRequest/{serviceName}/{serviceId}")
    public String processRequestForm(@PathVariable("serviceName") String serviceName,
                                    @PathVariable("serviceId") Integer serviceId,
                                    @Valid Request request, BindingResult result,
                                    Model model){
        
        //Asignar owner
        Owner owner = LoggedUser();
        
        if(owner!= null){
            //Asignar employee
            if(serviceName.equals("residence")){
            	System.out.println(serviceId + "ID");
                Residence residence = this.residenceService.findResidenceById(serviceId);
                System.out.println(residence + "HELLO");
                if (result.hasErrors()) {
                	System.out.println("ENTRA EN HAS ERRORS");
                	List<Pet> pets = owner.getPets();
                    model.addAttribute("pets", pets);
            		model.addAttribute("request", request);
                    model.addAttribute("service", "residence");
                    model.addAttribute("residence", residence);
                    return VIEW_CREATE_REQUEST;
                }
                
                if(request.getPet().equals(null)) {
                	List<Pet> pets = owner.getPets();
                    model.addAttribute("pets", pets);
                	result.rejectValue("pets", "Chose your pet", "Chose your pet");
                	model.addAttribute("request", request);
                    model.addAttribute("service", "residence");
                    model.addAttribute("residence", residence);
                    return VIEW_CREATE_REQUEST;
                }
                
                request.setOwner(owner);
                
                //Añadir request para Owner
                owner.addRequest(request);
                
                Employee emp = residence.getEmployees().stream()
                .skip((int) (residence.getEmployees().size()-1 * Math.random()))
                .findFirst().get();
                
                request.setEmployee(emp);
                
                //Añadir request para Service
                residence.addRequest(request);
                
            } else if(serviceName.equals("clinic")){
                Clinic clinic = this.clinicService.findClinicById(serviceId);
                if (result.hasErrors()) {
                	System.out.println("ENTRA EN HAS ERRORS");
                	model.addAttribute("request", request);
                    model.addAttribute("service", "clinic");
                    model.addAttribute("residence", clinic);
                    List<Pet> pets = owner.getPets();
                    model.addAttribute("pets", pets);
                    return VIEW_CREATE_REQUEST;
                }
                
                request.setOwner(owner);
                
                //Añadir request para Owner
                owner.addRequest(request);
                
                Employee emp = clinic.getEmployees().stream()
                .skip((int) (clinic.getEmployees().size()-1 * Math.random()))
                .findFirst().get();
                
                request.setEmployee(emp);
                
                //Añadir request para Service
                clinic.addRequest(request);
            }
            
            request.setRequestDate(LocalDateTime.now());
            try{
            	this.requestService.savePetVal(request);
                System.out.println("MASCOTA: ->>>>" +request.getPet());
                return "redirect:/owners/"+owner.getId()+"/myRequestList";
			}catch(RequestWithoutPetException ex){
				List<Pet> pets = owner.getPets();
                model.addAttribute("pets", pets);
            	result.rejectValue("pet", "Chose your pet", "Chose your pet");
            	model.addAttribute("request", request);
                
				if(serviceName.equals("residence")) {
                	System.out.println("ENTRA EN PETS ERRORS");
                	Residence residence = this.residenceService.findResidenceById(serviceId);
                	model.addAttribute("service", "clinic");
                    model.addAttribute("residence", residence);
                    
                }else{
                	System.out.println("ENTRA EN PETS ERRORS");
                	Residence clinic = this.residenceService.findResidenceById(serviceId);
                	model.addAttribute("service", "residence");
                    model.addAttribute("residence", clinic);
                }
				return VIEW_CREATE_REQUEST;
			}
            
        }
        
        return "redirect:/oups";
        
    }
}
//http://localhost:8080/createRequest/clinic/1