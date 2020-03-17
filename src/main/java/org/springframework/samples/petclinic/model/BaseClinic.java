/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 The services of the clinic
 */
@MappedSuperclass
public class BaseClinic extends BaseEntity {
	
	@Range(min = 0, max = 5)
	private int rating;
	
//	@OneToMany(cascade=CascadeType.ALL, mappedBy="service")
//	private Set<Payment> payments;
//	
	private String address;
	
	@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "service_id", referencedColumnName = "id")
	private Set<Employee> employees;
	
	@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "service_id", referencedColumnName = "id")
	private Set<Payment> payments;
	
	/*private DateFormat format = new SimpleDateFormat("HH:mm");
	*/
	@DateTimeFormat(pattern="HH:mm")
	private Date open;// = format.parse(new Date());
	
	@DateTimeFormat(pattern="HH:mm")
	private Date close;

	public String getRating() {
		return "The rating is " + this.rating + "stars";
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

//	public Set<Payment> getPayments() {
//		return payments;
//	}
//
//	public void setPayments(Set<Payment> payments) {
//		this.payments = payments;
//	}
//	
//	public void addPayment(Payment payment) {
//		this.payments.add(payment);
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getOpen() {
		return open;
	}

	public void setOpen(Date open) {
		this.open = open;
	}

	public Date getClose() {
		return close;
	}

	public void setClose(Date close) {
		this.close = close;
	}
	

}
