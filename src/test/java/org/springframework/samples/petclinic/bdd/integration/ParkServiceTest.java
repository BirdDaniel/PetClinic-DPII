package org.springframework.samples.petclinic.bdd.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Park;
import org.springframework.samples.petclinic.service.ParkService;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestPropertySource(locations = "classpath:application-mysql.properties")
class ParkServiceTest {                
    
    @Autowired
    protected ParkService parkService;

	@Test
    void shouldFindAllParks(){
        List<Park> parks = this.parkService.findAllParks();
        assertThat(parks)
        .isNotEmpty()
        .allMatch(x -> x.getAddress()!=null || x.getName()!=null);
    }

    @Test
    void shouldFindParkById(){
        Park park = this.parkService.findById(1);
        assertThat(park)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name","Parque del retiro");
    }

    @Test
    void shouldNotFindParkById(){
        Park park = this.parkService.findById(10);
        assertThat(park)
        .isNull();
    }
    
    @Test
    void shouldSavePark(){
        Park park = new Park();
        park.setName("Parque del test");
        park.setAddress("Calle delirio s/n");
        this.parkService.savePark(park);
        assertThat(this.parkService.findAllParks()).hasSize(6);
        assertThat(this.parkService.findById(6)).isEqualTo(park);
    }

    @Test
    void shouldNotSavePark(){
        Park park = new Park();
        assertThrows(ConstraintViolationException.class,() -> {this.parkService.savePark(park);});
    }

}


