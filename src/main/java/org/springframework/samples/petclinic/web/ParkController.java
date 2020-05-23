package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		Integer ownerId = this.ownerService.findIdByUsername(user.getUsername());

		return ownerId==null ? 0:ownerId;

    }
    
    @GetMapping("")
    public String getParks(Model model){
        
        
            List<Park> parks = this.parkService.findAllParks();
            model.addAttribute("loggedUser", isAuth());
            model.addAttribute("parks", parks);
            return VIEW_PARK_LIST;

    }

    @GetMapping("/{parkId}")
    public String getPark(@PathVariable("parkId") Integer parkId, Model model){
        
        if(isAuth()!=0){
            Park park = this.parkService.findById(parkId);
            model.addAttribute("loggedUser", isAuth());
            model.addAttribute("park", park);
            return "parks/parkView";
        }
        return "redirect:/oups";
    }

    @GetMapping("/new")
    public String createPark(Model model){

        Owner loggedUser = this.ownerService.findOwnerById(isAuth());
        
        if(loggedUser!=null){
            Park park = new Park();
            park.setOwner(loggedUser);
            model.addAttribute("loggedUser", loggedUser.getId());
            model.addAttribute(park);
            return VIEW_CREATE_OR_EDIT_PARK;
        }
        return "redirect:/oups";

    }

    @PostMapping("/new")
    public String saveCreatedPark(@Valid Park park, BindingResult result,Model model){
        Owner loggedUser = this.ownerService.findOwnerById(isAuth());

        if(result.hasErrors()){
            
            model.addAttribute("loggedUser", loggedUser.getId());
            model.addAttribute(park);
			return VIEW_CREATE_OR_EDIT_PARK;
        }else {
            park.setOwner(loggedUser);
            this.parkService.savePark(park);
            model.addAttribute("loggedUser", isAuth());
            return "redirect:/parks";
        }
        
    }

    @GetMapping("/{parkId}/edit")
    public String editPark(@PathVariable("parkId")Integer parkId, Model model){

        Integer loggedUser = isAuth();
        
        if(loggedUser!=0 ){
            Park park = this.parkService.findById(parkId);
            // park.setOwner(loggedUser);
            if(park.getOwner().getId()==loggedUser){
                model.addAttribute("loggedUser", loggedUser);
                model.addAttribute(park);
                return VIEW_CREATE_OR_EDIT_PARK;
            }
        }

        return "redirect:/oups";
    }

    @PostMapping("/{parkId}/edit")
    public String saveUpdatedPark(@PathVariable("parkId")Integer parkId,@Valid Park park, BindingResult result,Model model){
        
        if(result.hasErrors()){
            model.addAttribute("loggedUser", isAuth());
            model.addAttribute(park);
			return VIEW_CREATE_OR_EDIT_PARK;
        }

        Park parkToUpdate = this.parkService.findById(parkId);
        
        if(parkToUpdate.getOwner().getId()==isAuth()){
            BeanUtils.copyProperties(park, parkToUpdate, "id", "owner");
            this.parkService.savePark(parkToUpdate);
            model.addAttribute("loggedUser", isAuth());
            return "redirect:/parks";
        }

        return "redirect:/oups";
        
        
    }

    @GetMapping("/{parkId}/delete")
    public String deletePark(@PathVariable("parkId") Integer parkId, Model model) {
 
        Integer loggedUser = isAuth();

        if(loggedUser!=0){
            
            Park parkToDelete = this.parkService.findById(parkId);

            if(parkToDelete.getOwner().getId()==loggedUser){
                this.parkService.deletePark(parkId);
                return "redirect:/parks";
            }
        }
        
        return "redirect:/oups";
    }
}