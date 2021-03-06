package org.springframework.samples.petclinic.paypal;

import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Service;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PayPalController {
	
	@Autowired
	PaypalService paypalService;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	RequestService requestService;
	
	@Autowired
	ResidenceService residenceService; 
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@GetMapping("/pay/{requestId}")
	public String payment(@PathVariable("requestId") int requestId) {
		Request rq = this.requestService.findById(requestId);
		Service service = new Service();
		service = (this.clinicService.findClinicByRequest(rq)!=null)? this.clinicService.findClinicByRequest(rq):this.residenceService.findResidenceByRequest(rq);
		try {
			Payment payment = paypalService.createPayment(Double.valueOf(service.getPrice()), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
			
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	 @GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "paypal/cancel";
	    }

	    @GetMapping(value = SUCCESS_URL)
	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	        try {
	            Payment payment = paypalService.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	                return "paypal/success";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "redirect:/";
	    }
}
