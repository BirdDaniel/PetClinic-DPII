package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	        
	        
		@Test
		void shouldFindPetInResidence() {
	        
			Owner owner = this.ownerService.findOwnerById(2);
			
			Collection<Request> reqRes = this.requestService.findAcceptedResByOwnerId(owner.getId());
			assertThat(reqRes.size()).isGreaterThanOrEqualTo(1);
			assertThat(reqRes.stream().allMatch(x->x.getStatus()==true)).isTrue();
			assertThat(reqRes.stream().allMatch(x->this.residenceService.findResidenceByRequest(x)!=null)).isTrue();
			
			
	}

}
