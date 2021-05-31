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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.ApiException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;

/**
 * Servlet implementation class GetToken
 */
@WebServlet("/GetToken")
public class GetToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetToken() {
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
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = "sk_test_gfhA0NqKtqv20D2Ag3Fj6JX4";
		String amount = "1000";
		String currencyCd = "sgd";

		// Get the credit card details submitted by the form
		String paymentMethod = request.getParameter("paymentMethod");
        System.out.println(paymentMethod);
        
        StripePaymentIntent newPaymentIntent = new StripePaymentIntent();
        
        JsonObject paymentIntent = new JsonObject();
        
        paymentIntent = newPaymentIntent.CreatePaymentIntent(Stripe.apiKey, amount, currencyCd, paymentMethod);
        
        JSONObject obj = new JSONObject();

		/*String key = "paymentIntent";
		Object value = paymentIntent;
		obj.put(key, value);*/
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(paymentIntent);
        
		// Create a charge: this will charge the user's card
		/*try {
		  Map<String, Object> chargeParams = new HashMap<String, Object>();
		  chargeParams.put("amount", amount); // Amount in cents
		  chargeParams.put("currency", currencyCd);
		  chargeParams.put("source", token);
		  chargeParams.put("description", "Example charge");

		  Charge charge = Charge.create(chargeParams);
		  System.out.println(charge);
		} catch (CardException e) {
		  // The card has been declined
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
