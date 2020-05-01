package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedItemNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ItemServiceTests {
	
	@Autowired
	protected ItemService itemService;
	
	
//	@Test
//	void shouldFindRequestByOwner() {
//        
//		Owner owner = this.ownerService.findOwnerById(1);
//		assertThat(owner.getRequests().size()).isGreaterThanOrEqualTo(1);
//		assertThat(owner.getRequests().size()).isLessThanOrEqualTo(3);
//		
//		Request reqCli = this.requestService.findById(1);
//		Request reqRes = this.requestService.findById(4);
//		
//		assertThat(this.clinicService.findClinicByRequest(reqCli)).isNotNull();
//		assertThat(this.residenceService.findResidenceByRequest(reqRes)).isNotNull();
//		assertThat(this.clinicService.findClinicByRequest(reqRes)).isNull();
//		assertThat(this.residenceService.findResidenceByRequest(reqCli)).isNull();
//
//	}
	
	@Test
	@Transactional
	public void shouldInsertItem() throws DataAccessException, DuplicatedItemNameException {
		Item item = this.itemService.findItemById(1);

		Item item1 = new Item();
		item1.setName("Collares Caros");
		item1.setDescription("Description X");;
		item1.setPrice(50.);
		item1.setSale(0.2);
		item1.setStock(5);         
                
		this.itemService.saveItem(item1);
		assertThat(item1.getId().longValue()).isNotEqualTo(0);

		item = this.itemService.findItemById(item1.getId());
		assertThat(item).isNotEqualTo(null);
	}

	@Test
	@Transactional
	void shouldUpdateItem() throws DataAccessException, DuplicatedItemNameException {
		Item item = this.itemService.findItemById(1);
		String oldName = item.getName();
		String newName = oldName + "Mascarilla";

		item.setName(newName);
		this.itemService.saveItem(item);

		// retrieving new name from database
		item = this.itemService.findItemById(1);
		assertThat(item.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	void shouldDeleteItem() throws DataAccessException, DuplicatedItemNameException {
		Item item = this.itemService.findItemById(1);
		this.itemService.deleteItem(item);

		// retrieving new name from database
		item = this.itemService.findItemById(1);
		assertThat(item).isEqualTo(null);
	}

}
