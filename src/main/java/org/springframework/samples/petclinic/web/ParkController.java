package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Park;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ParkService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parks")
public class ParkController {

    private static final String VIEW_PARK_LIST = "parks/parkList";
    private static final String VIEW_CREATE_OR_EDIT_PARK = "parks/newPark";

    private final ParkService parkService;
    private final OwnerService ownerService;


    @Autowired
    public ParkController(ParkService parkService, OwnerService ownerService){
        this.parkService = parkService;
        this.ownerService = ownerService;
    }

    @InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

    private Integer isAuth(){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer ownerId = this.ownerService.findOwnerByUsername(user.getUsername()).getId();

		return ownerId==null ? 0:ownerId;

    }
    
    @GetMapping("")
    public String getParks(Model model){
        
        Integer userLogged = isAuth();

        List<Park> parks = this.parkService.findAllParks();
        model.addAttribute("loggedUser", userLogged);
        model.addAttribute("parks", parks);
        return VIEW_PARK_LIST;
        

    }

    @GetMapping("/new")
    public String createPark(Model model){

        Owner loggedUser = this.ownerService.findOwnerById(isAuth());
        Park park = new Park();
        park.setOwner(loggedUser);
        model.addAttribute("loggedUser", loggedUser.getId());
        model.addAttribute(park);
        return VIEW_CREATE_OR_EDIT_PARK;

    }

    @PostMapping("/new")
    public String saveCreatedPark(@Valid Park park, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute(park);
			return VIEW_CREATE_OR_EDIT_PARK;
        }else {
            this.parkService.savePark(park);
            model.addAttribute("loggedUser", isAuth());
            return "redirect:/parks";
        }
        

    }

}