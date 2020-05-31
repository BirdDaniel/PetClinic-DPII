package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.repository.ItemRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedItemNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ItemService {
	
	private ItemRepository itemRepository;

	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	
	@Transactional(readOnly = true)
	public Item findItemById(int id) throws DataAccessException {
		return itemRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedItemNameException.class)
	public void saveItem(Item item) throws DataAccessException, DuplicatedItemNameException {
		//creating item
		Item otherItem = null;
		List<Item> items;
		if(item.getId()!=null) {
			items=this.itemRepository.findItemWithIdDiferent(item.getName().toLowerCase(), item.getId());
			if(items.size()!=0) otherItem = items.get(0);
		}else {
			items=this.itemRepository.findItemWithIdDiferent(item.getName().toLowerCase());
			if(items.size()!=0) otherItem = items.get(0);
		}
		if (StringUtils.hasLength(item.getName()) &&  (otherItem!= null && otherItem.getId()!=item.getId())) {            	
        	throw new DuplicatedItemNameException();
        }else {
	    	itemRepository.save(item);
		}
	}
	
	@Transactional
	public void deleteItem(Item item){
		this.itemRepository.delete(item);
	}
}


