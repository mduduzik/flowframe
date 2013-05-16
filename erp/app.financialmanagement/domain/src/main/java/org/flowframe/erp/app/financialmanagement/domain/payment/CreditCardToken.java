package org.flowframe.erp.app.financialmanagement.domain.payment;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CreditCardToken extends Payment {
	private String token;
	private Date dateUsed;
	
	public CreditCardToken(){
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDateUsed() {
		return dateUsed;
	}
	public void setDateUsed(Date dateUsed) {
		this.dateUsed = dateUsed;
	}
	public CreditCardToken(String token) {
		super();
		this.token = token;
	}
}