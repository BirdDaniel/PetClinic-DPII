
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.EmployeeService;
import org.springframework.samples.petclinic.service.RequestService;
import org.springframework.samples.petclinic.service.ResidenceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employees/{employeeId}")
public class EmployeeController {

	private final RequestService	requestService;
	private final EmployeeService	employeeService;
	private final ClinicService		clinicService;
	private final ResidenceService	residenceService;
	private final static String		VIEW_MY_REQUESTS		= "employees/requests";
	private final static String		VIEW_MY_APPOINTMENTS	= "employees/appointments";


	@Autowired
	public EmployeeController(final EmployeeService employeeService, final RequestService requestService, final ClinicService clinicService, final ResidenceService residenceService) {

		this.requestService = requestService;
		this.employeeService = employeeService;
		this.clinicService = clinicService;
		this.residenceService = residenceService;

	}

	private boolean isAuth(final Employee employee) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer empId = this.employeeService.findByUsername(user.getUsername());
		return employee.getId() == empId;

	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("employee")
	public Employee findEmployee(@PathVariable("employeeId") final int employeeId) {
		return this.employeeService.findEmployeeById(employeeId);
	}

	@GetMapping("/requests")
	public String RequestsEmployee(final Employee employee, final Map<String, Object> model) {
		if (this.isAuth(employee)) {

			SortedSet<Request> res = new TreeSet<>(Comparator.comparing(Request::getRequestDate));
			Set<Request> requests = this.employeeService.getRequests(employee.getId());

			if (requests != null) {
				res.addAll(requests);
			}

			model.put("loggedUser", employee.getId());
			model.put("requests", res);
			return EmployeeController.VIEW_MY_REQUESTS;

		}

		return "redirect:/oups";

	}
	@GetMapping("/colleagues")
	public String ColleaguesEmployee(final Employee employee, final Map<String, Object> model) {
		if (this.isAuth(employee) && this.clinicService.findByEmployee(employee) != null) {
			Clinic clinic = this.clinicService.findByEmployee(employee);
			Collection<Employee> colleagues = this.employeeService.findEmployeeByClinicId(clinic.getId());
			colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("loggedUser", employee.getId());
			return "employees/colleagues";
		} else if (this.isAuth(employee) && this.residenceService.findByEmployee(employee) != null) {
			Residence residence = this.residenceService.findByEmployee(employee);
			Collection<Employee> colleagues = this.employeeService.findEmployeeByResidenceId(residence.getId());
			colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("loggedUser", employee.getId());
			return "employees/colleagues";
		}
		return "redirect:/oups";
	}
	@GetMapping("/{requestType}/{requestId}/assign")
	public String assignRequest(final Employee employee, @PathVariable("requestId") final int id, @PathVariable("requestType") final String requestType, final Map<String, Object> model) {

		if (this.isAuth(employee) && this.clinicService.findByEmployee(employee) != null) {

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			Clinic clinic = this.clinicService.findByEmployee(employee);
			Collection<Employee> colleagues = this.employeeService.findEmployeeByClinicId(clinic.getId());
			colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("request", request);
			model.put("assign", true);
			return "employees/colleagues";
		} else if (this.isAuth(employee) && this.residenceService.findByEmployee(employee) != null) {
			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			Residence residence = this.residenceService.findByEmployee(employee);
			Collection<Employee> colleagues = this.employeeService.findEmployeeByResidenceId(residence.getId());
			colleagues.remove(employee);
			model.put("colleagues", colleagues);
			model.put("request", request);
			model.put("assign", true);
			return "employees/colleagues";
		}

		return "redirect:/oups";

	}

	@GetMapping("/appointments")
	public String allAppointments(final Employee employee, final Map<String, Object> model) {

		if (this.isAuth(employee)) {
			Collection<Request> appointments = this.requestService.findAcceptedByEmployeeId(employee.getId());

			if (appointments != null) {
				model.put("appointments", appointments);
			}

			model.put("loggedUser", employee.getId());
			return EmployeeController.VIEW_MY_APPOINTMENTS;
		}

		return "redirect:/oups";

	}

	@GetMapping("/requests/{requestId}/accept")
	public String acceptRequest(final Employee employee, @PathVariable("requestId") final Integer id, final Map<String, Object> model) {

		if (this.isAuth(employee)) {

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);

			if (request != null) {
				request.setStatus(true);
				this.requestService.save(request);
			}

			return "redirect:/employees/{employeeId}/requests";
		}

		return "redirect:/oups";

	}
	@GetMapping("/{requestType}/{requestId}/{colleagueId}/reassign")
	public String reassignRequest(final Employee employee, @PathVariable("requestId") final int id, @PathVariable("requestType") final String requestType, @PathVariable("colleagueId") final int colleagueId, final Map<String, Object> model) {

		if (this.isAuth(employee) && this.employeeService.getRequests(employee.getId()).contains(this.requestService.findById(id))) {

			if (this.clinicService.findByEmployee(employee) != null) {
				Clinic clinic = this.clinicService.findByEmployee(employee);
				Collection<Employee> colleagues = this.employeeService.findEmployeeByClinicId(clinic.getId());
				if (colleagues.contains(this.employeeService.findEmployeeById(colleagueId))) {
					model.put("loggedUser", employee.getId());
					Request request = this.requestService.findById(id);
					Employee colleague = this.employeeService.findEmployeeById(colleagueId);
					model.put("assign", false);
					if (request != null) {

						request.setEmployee(colleague);
						this.requestService.save(request);

					}

					return "redirect:/employees/{employeeId}/requests";
				}
			} else if (this.residenceService.findByEmployee(employee) != null) {
				Residence residence = this.residenceService.findByEmployee(employee);
				Collection<Employee> colleagues = this.employeeService.findEmployeeByResidenceId(residence.getId());
				if (colleagues.contains(this.employeeService.findEmployeeById(colleagueId))) {
					model.put("loggedUser", employee.getId());
					Request request = this.requestService.findById(id);
					Employee colleague = this.employeeService.findEmployeeById(colleagueId);
					model.put("assign", false);
					if (request != null) {

						request.setEmployee(colleague);
						this.requestService.save(request);

					}

					return "redirect:/employees/{employeeId}/requests";
				}

			}

		}
		return "redirect:/oups";

	}

	@GetMapping("/{requestType}/{requestId}/decline")
	public String declineRequest(final Employee employee, @PathVariable("requestId") final int id, @PathVariable("requestType") final String requestType, final Map<String, Object> model) {

		if (this.isAuth(employee)) {

			model.put("loggedUser", employee.getId());
			Request request = this.requestService.findById(id);
			if (request != null) {
				request.setStatus(false);
				this.requestService.save(request);
			}
			if (requestType.equals("requests")) {
				return "redirect:/employees/{employeeId}/requests";
			} else {
				return "redirect:/employees/{employeeId}/appointments";
			}
		}

		return "redirect:/oups";

	}

}
