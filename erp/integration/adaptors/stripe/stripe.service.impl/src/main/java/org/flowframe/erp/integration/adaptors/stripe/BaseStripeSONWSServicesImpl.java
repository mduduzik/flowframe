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
	protected Long  defaultsCCNumber;
	protected Integer  defaultsCCExpMonth;
	protected Integer  defaultsCCExpYear;
	protected Integer  defaultsCCCvc;
	protected String  defaultsCCName;
	protected String  defaultsCCAddressLine1;
	protected String  defaultsCCAddressLine2;
	protected String  defaultsCCAddressCity;
	protected Integer  defaultsCCAddressZip;
	protected String  defaultsCCAddressState;
	protected String  defaultsCCAddressCountry;

	private Properties stripeProperties = new Properties();
	
	public void init() {
/*		loadStripeProperties();

		publicTestKey = stripeProperties.getProperty(STRIPE_PUB_TEST_KEY);
		privateTestKey = stripeProperties.getProperty(STRIPE_PRIV_TEST_KEY);
		publicProdKey = stripeProperties.getProperty(STRIPE_PUB_PROD_KEY);
		privateProdKey = stripeProperties.getProperty(STRIPE_PRIV_TPROD_KEY);
		*/
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

	public String getPublicTestKey() {
		return publicTestKey;
	}

	public void setPublicTestKey(String publicTestKey) {
		this.publicTestKey = publicTestKey;
	}

	public String getPrivateTestKey() {
		return privateTestKey;
	}

	public void setPrivateTestKey(String privateTestKey) {
		this.privateTestKey = privateTestKey;
	}

	public String getPublicProdKey() {
		return publicProdKey;
	}

	public void setPublicProdKey(String publicProdKey) {
		this.publicProdKey = publicProdKey;
	}

	public String getPrivateProdKey() {
		return privateProdKey;
	}

	public void setPrivateProdKey(String privateProdKey) {
		this.privateProdKey = privateProdKey;
	}

	public Long getDefaultsCCNumber() {
		return defaultsCCNumber;
	}

	public void setDefaultsCCNumber(Long defaultsCCNumber) {
		this.defaultsCCNumber = defaultsCCNumber;
	}

	public Integer getDefaultsCCExpMonth() {
		return defaultsCCExpMonth;
	}

	public void setDefaultsCCExpMonth(Integer defaultsCCExpMonth) {
		this.defaultsCCExpMonth = defaultsCCExpMonth;
	}

	public Integer getDefaultsCCExpYear() {
		return defaultsCCExpYear;
	}

	public void setDefaultsCCExpYear(Integer defaultsCCExpYear) {
		this.defaultsCCExpYear = defaultsCCExpYear;
	}

	public Integer getDefaultsCCCvc() {
		return defaultsCCCvc;
	}

	public void setDefaultsCCCvc(Integer defaultsCCCvc) {
		this.defaultsCCCvc = defaultsCCCvc;
	}

	public String getDefaultsCCName() {
		return defaultsCCName;
	}

	public void setDefaultsCCName(String defaultsCCName) {
		this.defaultsCCName = defaultsCCName;
	}

	public String getDefaultsCCAddressLine1() {
		return defaultsCCAddressLine1;
	}

	public void setDefaultsCCAddressLine1(String defaultsCCAddressLine1) {
		this.defaultsCCAddressLine1 = defaultsCCAddressLine1;
	}

	public String getDefaultsCCAddressLine2() {
		return defaultsCCAddressLine2;
	}

	public void setDefaultsCCAddressLine2(String defaultsCCAddressLine2) {
		this.defaultsCCAddressLine2 = defaultsCCAddressLine2;
	}

	public String getDefaultsCCAddressCity() {
		return defaultsCCAddressCity;
	}

	public void setDefaultsCCAddressCity(String defaultsCCAddressCity) {
		this.defaultsCCAddressCity = defaultsCCAddressCity;
	}

	public Integer getDefaultsCCAddressZip() {
		return defaultsCCAddressZip;
	}

	public void setDefaultsCCAddressZip(Integer defaultsCCAddressZip) {
		this.defaultsCCAddressZip = defaultsCCAddressZip;
	}

	public String getDefaultsCCAddressState() {
		return defaultsCCAddressState;
	}

	public void setDefaultsCCAddressState(String defaultsCCAddressState) {
		this.defaultsCCAddressState = defaultsCCAddressState;
	}

	public String getDefaultsCCAddressCountry() {
		return defaultsCCAddressCountry;
	}

	public void setDefaultsCCAddressCountry(String defaultsCCAddressCountry) {
		this.defaultsCCAddressCountry = defaultsCCAddressCountry;
	}
}
