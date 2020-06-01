package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RequestService {

  private RequestRepository requestRepository;	
	
//  @Autowired
//	private ClinicService clinicService;
//	
//	@Autowired
//	private ResidenceService residenceService;


	@Autowired
	public RequestService(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}	

	@Transactional(readOnly = true)
	public Request findById(int id) throws DataAccessException {
		return requestRepository.findById(id);
	}
	
	@Transactional
	public void save(Request request) {
		this.requestRepository.save(request);
	}

	@Transactional(readOnly = true)
	public Collection<Request> findAcceptedByOwnerId(int id) throws DataAccessException {
		return requestRepository.findAcceptedByOwnerId(id);
	}

	@Transactional(readOnly = true)
	public Collection<Request> findAcceptedByEmployeeId(int id) throws DataAccessException {
		return requestRepository.findAcceptedByEmployeeId(id);
	}
	 
	 @Transactional(readOnly = true)
	 public Collection<Request> findAcceptedResByOwnerId(int ownerId) throws DataAccessException{ 
		 return requestRepository.findAcceptedResByOwnerId(ownerId);
	 }



}
