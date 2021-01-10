/**
 * 
 */
package com.ajira.network.model;

import java.io.Serializable;

import org.springframework.lang.NonNull;

/**
 * @author raagul.s
 *
 */
public class DeviceRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6809505160176691654L;
	
	@NonNull
	private String type;
	@NonNull
	private String name;
	
	/**
	 * 
	 */
	public DeviceRequest() {
		super();
	}
	
	/**
	 * @param type
	 * @param name
	 */
	public DeviceRequest(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
