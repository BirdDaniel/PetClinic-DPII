package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.RequestRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RequestService {

  private RequestRepository requestRepository;	
	
  @Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ResidenceService residenceService;


	@Autowired
	public RequestService(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}	

	@Transactional(readOnly = true)
	public Request findById(int id) throws DataAccessException {
		return requestRepository.findById(id);
	}
  
  @Transactional(readOnly = true)
	public Collection<Request> findAcceptedRequests() throws DataAccessException {
		return requestRepository.findAcceptedAll();
	}
	
	@Transactional
	public void save(Request request) {
		this.requestRepository.save(request);
	}

}
