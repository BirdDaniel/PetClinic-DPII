package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Person;

public interface PersonRepository {

    //Dado un username, devuelve a la persona que lo tenga
    Person findByUsername(String username);



}
