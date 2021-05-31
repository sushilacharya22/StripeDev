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

import org.json.JSONArray;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.CountrySpec;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;

/**
 * Servlet implementation class GetCustomer
 */
@WebServlet("/GetCountrySpec")
public class GetCountrySpec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCountrySpec() {
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
		
		String token = request.getParameter("token");
		
		System.out.println(token);
		
		 CountrySpec countrySpec = null;
		 
		 try {
			countrySpec = CountrySpec.retrieve("SG");
		} catch (StripeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		 System.out.println(countrySpec);
		 
		 Map<String, Object> cardPayments = new HashMap<>();
		  cardPayments.put("requested", true); 
		  Map<String, Object> transfers = new  HashMap<>(); 
		  transfers.put("requested", true); 
		  Map<String, Object> capabilities = new HashMap<>(); 
		  capabilities.put("card_payments", cardPayments); 
		  capabilities.put("transfers", transfers); 
		  Map<String, Object> params = new HashMap<>(); 
		  params.put("type", "custom");
		  params.put("country", "SG"); 
		  params.put("email", "sushil.handyman@example.com");
		  params.put("capabilities", capabilities);
		  params.put("external_account", token);
		  
		  Account account = null; 
		  try {
			account = Account.create(params);
		} catch (StripeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		  
		  System.out.println(account); System.out.println(account.getId());
		 
		
		Map<String, Object> params1 = new HashMap<>();
		params1.put("account", account.getId());
		params1.put(
		  "refresh_url",
		  "https://example.com/reauth"
		);
		params1.put(
		  "return_url",
		  "https://example.com/return"
		);
		params1.put("type", "account_update");
		params1.put("collect", "currently_due");
		
		AccountLink accountLink = null;
		
		try {
			accountLink =
			  AccountLink.create(params1);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(accountLink);
		
		response.setContentType("plain/text");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(accountLink);
	}

}
