package org.springframework.samples.petclinic.paypal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {
	
	private String clientId = "Ae4fuH0CW5RbKBb37pDPy38C36xWe4synIsw-p6X6HsBpiJImFYp0XQJ7VxK2ndtci4x5Z2K9EWGcWNP";
	private String clientSecret = "ELuMyY0MHgAOlELsmx6_H1iJczcuYnFG-AuaJbLY4OXPC_eKhrpx2w1IGaQcXJNeJWKo4vwt-pY-EkG6";
	
	public Payment createPayment(
			Double total,  
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(total));

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		
		APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
		Payment createdPayment = payment.create(apiContext);
		
		return createdPayment;
		
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
		return payment.execute(apiContext, paymentExecute);
	}

}
