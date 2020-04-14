package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RequestController {

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
            
        System.out.println("Service: "+ serviceName + ", Id: "+ serviceId);
        //COMPROOBAR QUE SE ESTÁ LOGUEADO
        Owner userLogged = LoggedUser();

        if(userLogged!=null){
            //CREAR UNA REQUEST Y 
            System.out.println(userLogged);
            Request request = new Request();

        //ASIGNARLA AL OWNER LOGGEADO, 

            userLogged.addRequest(request);

        //AL SERVICE DESDE EL QUE SE HACE
            if(serviceName.equals("residence")){
                Residence residence = this.residenceService.findResidenceById(serviceId);
                residence.addRequest(request);
                System.out.println(residence.getRequests());
                Employee emp = residence.getEmployees().stream()
                .skip((int) (residence.getEmployees().size()-1 * Math.random()))
                .findFirst().get();

                //Y A UN EMPLOYEE ALEATORIO
                request.setEmployee(emp);

                model.addAttribute("request", request);
                model.addAttribute("service", "residence");
                model.addAttribute("residence", residence);

            }else if(serviceName.equals("clinic")) {
                Clinic clinic = this.clinicService.findClinicById(serviceId);
                clinic.addRequest(request);

                Employee emp = clinic.getEmployees().stream()
                .skip((int) (clinic.getEmployees().size()-1 * Math.random()))
                .findFirst().get();

                request.setEmployee(emp);

                model.addAttribute("attributeName", "attributeValue");
            }

            
            
        }



        /*
        
        */

        return "requests/createRequest";
    }
}