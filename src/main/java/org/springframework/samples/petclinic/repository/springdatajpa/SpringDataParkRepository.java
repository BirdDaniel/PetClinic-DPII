package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Park;
import org.springframework.samples.petclinic.repository.ParkRepository;

public interface SpringDataParkRepository extends ParkRepository, Repository<Park, Integer> {


	@Query("SELECT park FROM Park park WHERE park.id =:id")
    Park findById(@Param("id") Integer id);

    @Query("SELECT park FROM Park park")
    List<Park> findAllParks();

}