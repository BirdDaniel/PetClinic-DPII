package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.repository.ItemRepository;

public interface SpringDataItemRepository extends ItemRepository, Repository<Item, Integer>{
	
	@Query("SELECT i,r,c FROM Item i,Residence r,Clinic c WHERE i.name=:it")
	public Collection<Item> findItemByNameInService(@Param("it")String item);
}
