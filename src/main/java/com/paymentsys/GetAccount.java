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
import org.json.JSONObject;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;

/**
 * Servlet implementation class GetAccount
 */
@WebServlet("/GetAccount")
public class GetAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Stripe.apiKey = "sk_test_gfhA0NqKtqv20D2Ag3Fj6JX4";
		
		String accountId = request.getParameter("accountId");
		
		System.out.println(accountId);
		
		Account account = null;
		
		try {
			account =  Account.retrieve(accountId);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String externalSource = account.getExternalAccounts().toJson();
		
		JSONObject jsonObj = new JSONObject(externalSource.toString());
		
		JSONArray arr = jsonObj.getJSONArray("data");
		
		System.out.println(arr);
		
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
		params1.put("type", "account_onboarding");
		params1.put("collect", "eventually_due");
		
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
		out.print(account.getChargesEnabled());
		out.print(account.getPayoutsEnabled());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
