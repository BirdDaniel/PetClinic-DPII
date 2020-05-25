package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional(readOnly = true)
	public Employee findEmployeeById(int id) throws DataAccessException {
		return employeeRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Integer findByUsername(String username) {
		return this.employeeRepository.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Set<Request> getRequests(int id) {
		return this.employeeRepository.getRequests(id);
	}

	@Transactional(readOnly = true)
	public Collection<Employee> findEmployeesByLastName(String lastName) throws DataAccessException {
		return employeeRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveEmployees(Set<Employee> employees) {
		for (Employee emp : employees) {
			saveEmployee(emp);
		}
	}
	
	@Transactional
	public Iterable<Employee> findAll(){
		return employeeRepository.findAll();
	}

	@Transactional
	public void saveEmployee(Employee employee) throws DataAccessException {
		// creating Employee
		employeeRepository.save(employee);
		// creating user
		userService.saveUser(employee.getUser());
		// creating authorities
		authoritiesService.saveAuthorities(employee.getUser().getUsername(), "employee");
	}
	@Transactional
	public Collection<Employee> findEmployeeByClinicId(int id){
		return employeeRepository.findEmployeeByClinicId(id);
	}
	@Transactional
	public Collection<Employee> findEmployeeByResidenceId(int id){
		return employeeRepository.findEmployeeByResidenceId(id);
	}

	public Set<Request> getRequestsPayed(Integer id) {
		return this.employeeRepository.getRequestsPayed(id);
	}
}
