package com.paymentsys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

/**
 * Servlet implementation class AddStripeCustomer
 */
@WebServlet("/AddStripeCustomer")
public class AddStripeCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStripeCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Stripe.apiKey = "sk_test_gfhA0NqKtqv20D2Ag3Fj6JX4";

		// Get the credit card details submitted by the form
		String paymentMethod = request.getParameter("paymentMethod");
        System.out.println(paymentMethod);
        
		Map<String, Object> chargeParams = new HashMap<>();
		//chargeParams.put("source", token);
		chargeParams.put("payment_method", paymentMethod);
		chargeParams.put("email", "sushil.acharya82@gmail.com");	
		
		Map<String, String> metaDeta = new HashMap<String, String>();
		metaDeta.put("name", "testUser");
		
		chargeParams.put("metadata", metaDeta);		
		
		Map<String, String> invoiceSettings = new HashMap<String, String>();
		invoiceSettings.put("default_payment_method", paymentMethod);
		
		chargeParams.put("invoice_settings", invoiceSettings);	
		
		try {
			Customer customer = Customer.create(chargeParams);
			System.out.println(customer);
			CustomerPaymentIntent newCustomerPayment = new CustomerPaymentIntent();
			JsonObject obj = newCustomerPayment.CreatePaymentIntent("sk_test_gfhA0NqKtqv20D2Ag3Fj6JX4", "999", "sgd", paymentMethod, customer.getId());			
			System.out.println(obj);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(obj);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
