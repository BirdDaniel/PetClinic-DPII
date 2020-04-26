package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RequestServiceTests {
	
	@Autowired
	protected RequestService requestService;	
	 
	@Autowired
	protected ResidenceService residenceService;	
	 
	@Autowired
	protected PetService petService;
	        
	@Autowired
	protected OwnerService ownerService;
	
	@BeforeEach
	void setup(){
	}

	//----------------- Encontrar Requests --------------------//

	@WithMockUser(value = "owner1")
	@Test
	void shouldFindRequest(){
		assertThat(this.requestService.findById(1))
		.hasFieldOrPropertyWithValue("finishDate", LocalDateTime.of(2030, 8, 12, 12, 0))
		.hasFieldOrPropertyWithValue("serviceDate", LocalDateTime.of(2021, 8, 5, 16, 0) );

	}

	@WithMockUser(value = "owner1")
	@Test
	void shouldNotFindRequest(){
		assertThat(this.requestService.findById(18)).isNull();

	}


	//-------------Requests de un Employee--------------//
	
	@Test
	void shouldFindRequestsByEmployeeId(){
		assertThat(this.requestService.findAcceptedByEmployeeId(1))
		.isNotEmpty()
		.allMatch(x -> x.getEmployee().getId()==1);
	}

	@Test
	void shouldNotFindRequestsByEmployeeId(){
		assertThat(this.requestService.findAcceptedByEmployeeId(12))
		.isEmpty();
	}

	@Test
	void shouldFindAppointmentsByEmployeeId(){
		assertThat(this.requestService.findAcceptedByEmployeeId(1))
		.isNotEmpty()
		.allMatch(x -> x.getEmployee().getId()==1);
	}

	@Test
	void shouldNotFindAppointmentsByEmployeeId(){
		assertThat(this.requestService.findAcceptedByEmployeeId(19))
		.isEmpty();
	}


	//---------------------Requests de un Owner----------------------------//
	@Test
	void shouldFindRequestsByOwnerId(){
		assertThat(this.requestService.findAcceptedByOwnerId(1))
		.isNotEmpty()
		.allMatch(x -> x.getOwner().getId()==1);
	}

	@Test
	void shouldNotFindRequestsByOwnerId(){
		assertThat(this.requestService.findAcceptedByOwnerId(12))
		.isEmpty();
	}


	@Test
	void shouldFindAppointmentsByOwnerId(){
		assertThat(this.requestService.findAcceptedByOwnerId(1))
		.isNotEmpty()
		.allMatch(x -> x.getOwner().getId()==1);
	}

	@Test
	void shouldNotFindAppointmentsByOwnerId(){
		assertThat(this.requestService.findAcceptedByOwnerId(19))
		.isEmpty();
	}


	//-------------Pets en una Residence---------------//
	@Test
	void shouldFindPetInResidence() {
		
		assertThat(this.requestService.findAcceptedResByOwnerId(1))
		.isNotEmpty()
		.allMatch(x -> x.getOwner().getId()==1)
		.allMatch(x -> x.getStatus()==true)
		.allMatch(x -> LocalDateTime.now().isAfter(x.getServiceDate()) 
					&& LocalDateTime.now().isBefore(x.getFinishDate()));
					
	}

}
