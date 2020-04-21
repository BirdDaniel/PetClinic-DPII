package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Park;
import org.springframework.samples.petclinic.repository.ParkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkService {

    private ParkRepository parkRepository;

    @Autowired
    public ParkService(ParkRepository parkRepository){
        this.parkRepository = parkRepository;
    }

    @Transactional(readOnly = true)
    public Park findById(Integer id) {
        return this.parkRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Park> findAllParks() throws DataAccessException{
        return this.parkRepository.findAllParks();
    }

    @Transactional
    public void savePark(Park park){
        this.parkRepository.save(park);
    }


}