package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.Collection;

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
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RequestController {

    private final static String VIEW_CREATE_REQUEST = "requests/createRequest";
    private final RequestService requestService;
    private final ClinicService clinicService;
    private final ResidenceService residenceService;
    private final OwnerService ownerService;
//    private final AuthoritiesService authoritiesService;

    private Owner loggedUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Owner loggedUser = this.ownerService.findOwnerByUsername(user.getUsername());
		return loggedUser; 
	}
//    private Owner loggedUser() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String rol = this.authoritiesService.findById(user.getUsername()).getAuthority();
//        
//        if(rol.equals("owner")){
//        Owner loggedUser = this.ownerService.findOwnerByUsername(user.getUsername());
//        return loggedUser; 
//        }
//        return null;
//	}
    
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
	
//	@Autowired
//    public RequestController(RequestService requestService,
//                            ClinicService clinicService, 
//                            ResidenceService residenceService,
//                            OwnerService ownerService,
//                            AuthoritiesService authoritiesService) {
//
//        this.ownerService = ownerService;
//        this.residenceService = residenceService;
//        this.clinicService = clinicService;
//		this.requestService = requestService;
//		this.authoritiesService = authoritiesService;
//
//    }
	
	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		return loggedUser().getPets();
	}
	
//	@InitBinder("request")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new RequestValidator());
//	}
    
    @GetMapping("/createRequest/{serviceName}/{serviceId}")
    public String createRequestForm(@PathVariable("serviceName") String serviceName, 
                                    @PathVariable("serviceId") int serviceId, ModelMap model){
            
        //COMPROBAR QUE SE ESTÁ LOGUEADO
        Owner userLogged = loggedUser();
        if(userLogged!=null){

            //CREAR UNA REQUEST
            Request request = new Request();

            model.addAttribute("loggedUser", userLogged.getId());
            
            //ASIGNAR OWNER A LA REQUEST
            request.setOwner(userLogged);

            //ASIGNAR HORA Y FECHA DE SOLICITUD
            request.setRequestDate(LocalDateTime.now());

            //Coger el SERVICE DESDE EL QUE SE HACE
            if(serviceName.equals("residence")){
                Residence residence = this.residenceService.findResidenceById(serviceId); 
                
                model.addAttribute("request", request);
                model.addAttribute("service", "residence");
                model.addAttribute("residence", residence);
                
            }else if(serviceName.equals("clinic")) {                
                Clinic clinic = this.clinicService.findClinicById(serviceId);
                
                request.setFinishDate(LocalDateTime.of(2050, 12, 29, 23, 59));
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
                                    ModelMap model){
        
        //Comprobar que está loggeado        
        Owner owner = loggedUser();
    
        if(owner!=null){

            if(result.hasErrors()){
                model.addAttribute("service", serviceName);
                model.addAttribute("request",request);
                model.addAttribute("loggedUser", loggedUser().getId());
                return VIEW_CREATE_REQUEST;
            }

            //Asignar request a Owner
            owner.addRequest(request);
            request.setOwner(owner);

            //Añadir request al service
            //Asignar Employee
            if(serviceName.equals("residence")){
                Residence residence = this.residenceService.findResidenceById(serviceId);
                
                residence.addRequest(request);

                Employee emp = residence.getEmployees().stream()
                		.skip((int) (residence.getEmployees().size()-1 * Math.random()))
                        .findFirst().get();

                request.setEmployee(emp);
            
            }else if(serviceName.equals("clinic")){
                Clinic clinic = this.clinicService.findClinicById(serviceId);
                
                clinic.addRequest(request);

                Employee emp = clinic.getEmployees().stream()
                		.skip((int) (clinic.getEmployees().size()-1 * Math.random()))
                        .findFirst().get();

                request.setEmployee(emp);
            }
            this.requestService.save(request);
            return "redirect:/owners/"+owner.getId()+"/myRequestList";
        }
        
        
        return "redirect:/oups";
        
    }
}
//http://localhost:8080/createRequest/clinic/1