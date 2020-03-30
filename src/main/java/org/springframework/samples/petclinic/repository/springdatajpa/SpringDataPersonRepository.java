package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Person;

public interface SpringDataPersonRepository {

    @Query("SELECT person FROM Person person WHERE person.user.username=:username")
    public Person findByUsername(@Param("username") String username);
    

}