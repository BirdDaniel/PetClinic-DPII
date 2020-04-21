package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Park;

public interface ParkRepository {

    Park findById(Integer id);

    List<Park> findAllParks() throws DataAccessException;

    void save(Park park) throws DataAccessException;

}