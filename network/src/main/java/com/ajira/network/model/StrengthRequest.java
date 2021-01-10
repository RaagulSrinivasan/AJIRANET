package com.ajira.network.model;

import java.io.Serializable;

public class StrengthRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -243755677298417939L;
	
	private String value;

	/**
	 * 
	 */
	public StrengthRequest() {
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
