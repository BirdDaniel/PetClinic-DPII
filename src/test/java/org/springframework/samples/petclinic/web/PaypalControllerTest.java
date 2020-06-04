package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import io.restassured.http.ContentType;
import lombok.extern.java.Log;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Log
public class PaypalControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void paypalTest() throws PayPalRESTException {
		/*
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("2");

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);  
		
		PaymentExecution paymentExecute = new PaymentExecution();
		APIContext apiContext = new APIContext("Ae4fuH0CW5RbKBb37pDPy38C36xWe4synIsw--p6X6HsBpiJImFYp0XQJ7VxK2ndtci4x5Z2K9EWGcWNP", "ELuMyY0MHgAOlELsmx6_H1iJczcuYnFG-AuaJbLY4OXPC_eKhrpx2w1IGaQcXJNeJWKo4vwt-pY-EkG6", "sandbox");
		payment.execute(apiContext, paymentExecute);
		
		
		given()
    		.request().contentType(ContentType.JSON)
		 			.log().all()
		 	.response().log().all()		
		.with()	    
		.body(amount)
		.with()	    
		.body(transaction)
		.with()	    
		.body(payer)
		.with()	    
		.body(payment).*/
		 when()
			.get("http://www.dp2.com/pay/1")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void paypalSuccessTest() {
		when()
			.get("http://www.dp2.com/pay/success")
		.then()
			.statusCode(200);
	}

}
