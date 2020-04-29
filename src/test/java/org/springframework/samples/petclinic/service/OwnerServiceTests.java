
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Request;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class OwnerServiceTests {                
        @Autowired
	protected OwnerService ownerService;
        
        @Autowired
    	protected RequestService requestService;
        
        
        @Autowired
    	protected ClinicService clinicService;

        
        @Autowired
    	protected ResidenceService residenceService;


	@Test
	void shouldFindOwnersByLastName() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Schroeder");
		assertThat(owners.size()).isEqualTo(1);

		owners = this.ownerService.findOwnerByLastName("Daviss");
		assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.ownerService.findOwnerById(1);
		assertThat(owner.getLastName()).isEqualTo("Seder");
		assertThat(owner.getPets().size()).isEqualTo(2);
		assertThat(owner.getPets().get(0).getType()).isNotNull();
		assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("bird");
	}

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		owner.setAddress("4, Evans Street");
		//owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
                User user=new User();
                user.setUsername("Sam");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                owner.setUser(user);                
                
		this.ownerService.saveOwner(owner);
		assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.ownerService.findOwnerByLastName("Schultz");
		assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Owner owner = this.ownerService.findOwnerById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.ownerService.saveOwner(owner);

		// retrieving new name from database
		owner = this.ownerService.findOwnerById(1);
		assertThat(owner.getLastName()).isEqualTo(newLastName);
	}

	@Test
	void shouldFindRequestByOwner() {
        
		Owner owner = this.ownerService.findOwnerById(1);
		assertThat(owner.getRequests().size()).isGreaterThanOrEqualTo(1);
		assertThat(owner.getRequests().size()).isLessThanOrEqualTo(3);
		
		Request reqCli = this.requestService.findById(1);
		Request reqRes = this.requestService.findById(4);
		
		assertThat(this.clinicService.findClinicByRequest(reqCli)).isNotNull();
		assertThat(this.residenceService.findResidenceByRequest(reqRes)).isNotNull();
		assertThat(this.clinicService.findClinicByRequest(reqRes)).isNull();
		assertThat(this.residenceService.findResidenceByRequest(reqCli)).isNull();

	}

}
