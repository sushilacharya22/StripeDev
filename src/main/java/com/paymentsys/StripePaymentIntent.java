package com.paymentsys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public class StripePaymentIntent {

	public JsonObject CreatePaymentIntent (String apiKey, String amount, String currencyCd, String paymentMethod) {
		
		Stripe.apiKey = apiKey;
		String PaymentIntentId = null;
		
		Map<String, Object> paymentintentParams = new HashMap<String, Object>();
		paymentintentParams.put("amount", 1099);
		paymentintentParams.put("currency", "sgd");
		ArrayList<String> payment_method_types = new ArrayList<String>();
		payment_method_types.add("card");
		paymentintentParams.put("payment_method_types", payment_method_types);
		paymentintentParams.put("payment_method", paymentMethod);
		paymentintentParams.put("confirm", true);
		Map<String, Object> paymentMethodCard = new HashMap<String, Object>();
		paymentMethodCard.put("request_three_d_secure", "any");
		Map<String, Object> paymentMethodOptions = new HashMap<String, Object>();
		paymentMethodOptions.put("card", paymentMethodCard);
		paymentintentParams.put("payment_method_options", paymentMethodOptions);
		paymentintentParams.put("return_url", "https://localhost:8443/StripePayment/index1.html");
		
		PaymentIntent newPaymentIntent = new PaymentIntent();		
		try {
			newPaymentIntent = PaymentIntent.create(paymentintentParams);
			//System.out.println(newPaymentIntent);
			PaymentIntentId = newPaymentIntent.getId();
			newPaymentIntent.getRawJsonObject();
			//System.out.println(newPaymentIntent.getRawJsonObject());
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return newPaymentIntent.getRawJsonObject();
		}
		/*PaymentIntent intent;
		try {
			intent = PaymentIntent.retrieve(PaymentIntentId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("return_url", "http://localhost:8091/StripePayment/");
			intent.confirm(params);
			System.out.println(intent);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
