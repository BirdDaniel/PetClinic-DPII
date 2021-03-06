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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

/**
 The services of the clinic
 */
@Entity
@Table(name = "items")
public class Item extends NamedEntity {

	@Range(min= (long) 0.1)
	private double price;

	@Range(min= (long) 0.1, max =1)
	private double sale;
	
	@NotEmpty
	@Column(length=1024)
	private String description;
	
	@Range(min= 0)
	private int stock;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="shop_id")
//	private Shop shop;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSale() {
		return sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "(ID = " + this.id + ", NOMBRE = " + this.getName() + "," +this.description + "," + this.price + "," + this.sale + "," + this.stock + ")";
	}

}
