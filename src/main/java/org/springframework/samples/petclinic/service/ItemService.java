package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.model.Residence;
import org.springframework.samples.petclinic.repository.ClinicRepository;
import org.springframework.samples.petclinic.repository.ItemRepository;
import org.springframework.samples.petclinic.repository.ResidenceRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedItemNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {
	
	private ItemRepository itemRepository;
	private ClinicService clinicService;
	private ResidenceService residenceService;
	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Transactional(rollbackFor = DuplicatedItemNameException.class)
	public void saveItem(Item item, Clinic clinic) throws DataAccessException, DuplicatedItemNameException {
		//creating item
		Collection<Item> otherItem = this.itemRepository.findItemByNameInService(item.getName());
		System.out.println(otherItem);
        if (otherItem.size() != 0) {            	
        	throw new DuplicatedItemNameException();
        }else {
        	clinic.addItems(item);
        	itemRepository.save(item);
		}
	}

	@Transactional(rollbackFor = DuplicatedItemNameException.class)
	public void saveItem(Item item, Residence residence) throws DataAccessException, DuplicatedItemNameException {
		//creating item
		Collection<Item> otherItem = this.itemRepository.findItemByNameInService(item.getName());
		System.out.println(otherItem);
	    if (otherItem.size() != 0) {            	
	    	throw new DuplicatedItemNameException();
	    }else {
	    	residence.addItems(item);
	    	itemRepository.save(item);
		}
	}
}


