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
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;

/**
 * Servlet implementation class GetCustomer
 */
@WebServlet("/GetCustomer")
public class GetCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomer() {
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
		
		String customerId = request.getParameter("customer");
		System.out.println(customerId);
		JSONArray CardsArray = new JSONArray();
		Customer customer = new Customer();
		PaymentMethodCollection paymentMethodCollection = new PaymentMethodCollection();
		try {
			Map<String, Object> params =
				    new HashMap<String, Object>();
				params.put("customer", customerId);
				params.put("type", "card");

				paymentMethodCollection = PaymentMethod.list(params);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(paymentMethodCollection.getRawJsonObject());
		
		response.setContentType("plain/text");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(paymentMethodCollection.getRawJsonObject());
	}

}
