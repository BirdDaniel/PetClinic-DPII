package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Item;
import org.springframework.samples.petclinic.repository.ItemRepository;

public interface SpringDataItemRepository extends ItemRepository, Repository<Item, Integer>{
	
	@Query("SELECT i,r,c FROM Item i,Residence r,Clinic c WHERE i.name=:it")
	public Collection<Item> findItemByNameInService(@Param("it")String item);
	
//	@Query(value = "SELECT ite FROM Item ite WHERE LOWER(ite.name)=:name AND ite.id<>:id order by ite.name limit 0, 1", nativeQuery = true)
//	public Item findItemWithIdDiferent(@Param("name")String name, @Param("id")int id);
//	
//	@Query(value = "SELECT ite FROM Item ite WHERE LOWER(ite.name)=:name order by ite.name limit 0, 1", nativeQuery = true)
//	public Item findItemWithIdDiferent(@Param("name")String name);
	
	@Query("SELECT item FROM Item item WHERE LOWER(item.name)=:name AND item.id<>:id")
	public List<Item> findItemWithIdDiferent(@Param("name")String name, @Param("id")int id);
	
	@Query("SELECT item FROM Item item WHERE LOWER(item.name)=:name")
	public List<Item> findItemWithIdDiferent(@Param("name")String name);
	
}
