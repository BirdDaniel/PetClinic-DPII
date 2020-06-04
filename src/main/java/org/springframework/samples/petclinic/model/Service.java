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

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
/**
 The services of the clinic
 */
@MappedSuperclass
public class Service extends NamedEntity {

	@Range(min = 0, max = 5)
	private int rating;

	private String address;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected Set<Employee> employees;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Payment> payments;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Request> requests;
	/*private DateFormat format = new SimpleDateFormat("HH:mm");
	*/
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime open;// = format.parse(new Date());
	
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime close;

	@Range(min = 1)
	private int max;
	
	@Range(min = (long)0.1)
	private double price;
	
	
	@NotEmpty
	@Column(length = 1024)
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Item> items;

	
	public String getRating() {
		return "The rating is " + this.rating + " stars";
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	
	public void addPayment(Payment payment) {
		this.payments.add(payment);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalTime getOpen() {
		return open;
	}

	public void setOpen(LocalTime open) {
		this.open = open;
	}

	public LocalTime getClose() {
		return close;
	}

	public void setClose(LocalTime close) {
		this.close = close;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<Request> getRequests() {
		return requests;
	}

	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addRequest(Request request) {
		this.requests.add(request);
	}
	
	public void removeRequest(Request request) {
		this.requests.remove(request);
	}
	
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public void addItems(Item item) {
		this.items.add(item);
	}
	
	public void removeItems(Item item) {
		if(this.items.contains(item)) 
			this.items.remove(item);
			
	}
	
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}
	
	public void removeEmployee(Employee employee) {
		if(this.employees.contains(employee)) this.employees.remove(employee);
	}
}
