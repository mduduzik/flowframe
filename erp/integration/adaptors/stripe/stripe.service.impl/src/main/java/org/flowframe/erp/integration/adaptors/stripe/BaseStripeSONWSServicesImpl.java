package org.flowframe.erp.integration.adaptors.stripe;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stripe.Stripe;

public abstract class BaseStripeSONWSServicesImpl {
	private static final String STRIPE_PUB_TEST_KEY = "stripe.pub.test.key";
	private static final String STRIPE_PRIV_TEST_KEY = "stripe.priv.test.key";
	private static final String STRIPE_PUB_PROD_KEY = "stripe.pub.prod.key";
	private static final String STRIPE_PRIV_TPROD_KEY = "stripe.priv.prod.key";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String publicTestKey = null;
	protected String privateTestKey = null;

	protected String publicProdKey = null;
	protected String privateProdKey = null;

	private Properties stripeProperties = new Properties();
	
	public void init() {
		loadStripeProperties();

		publicTestKey = stripeProperties.getProperty(STRIPE_PUB_TEST_KEY);
		privateTestKey = stripeProperties.getProperty(STRIPE_PRIV_TEST_KEY);
		publicProdKey = stripeProperties.getProperty(STRIPE_PUB_PROD_KEY);
		privateProdKey = stripeProperties.getProperty(STRIPE_PRIV_TPROD_KEY);
		
		Stripe.apiKey = privateTestKey;
	}

	protected Properties loadStripeProperties() {
		if (!stripeProperties.isEmpty()) {
			return stripeProperties;
		}
		try {
			stripeProperties.load(StripeServicesImpl.class.getResourceAsStream("/stripe.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load stripe.properties", e);
		}

		return stripeProperties;
	}
	
	protected static String getUniquePlanId() {
		return String.format("FF-PLAN-%s", UUID.randomUUID());
	}

	protected static String getUniqueCouponId() {
		return String.format("FF-COUPON-%s", UUID.randomUUID());
	}
	
}
