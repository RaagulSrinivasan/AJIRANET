package com.ajira.network.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Device implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8695614498179786416L;
	private String type;
	private String name;
	private Integer strength;
	
	/**
	 * 
	 */
	public Device() {
		super();
	}

	/**
	 * @param type
	 * @param name
	 * @param strength
	 */
	public Device(String type, String name, Integer strength) {
		super();
		this.type = type;
		this.name = name;
		this.strength = strength;
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

	/**
	 * @return the strength
	 */
	@JsonIgnore
	public Integer getStrength() {
		return strength;
	}

	/**
	 * @param strength the strength to set
	 */
	public void setStrength(Integer strength) {
		this.strength = strength;
	}
	
}
