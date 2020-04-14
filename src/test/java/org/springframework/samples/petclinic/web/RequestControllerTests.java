package org.springframework.samples.petclinic.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

@WebMvcTest(controllers = EmployeeController.class,
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
				excludeAutoConfiguration= SecurityConfiguration.class)
class RequestControllerTests {

    
	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private RequestController requestController;

    @WithMockUser(value = "owner1")
    @Test
    void makeRequestInitForm(){
    }
}