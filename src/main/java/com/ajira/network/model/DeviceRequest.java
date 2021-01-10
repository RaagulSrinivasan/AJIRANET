/**
 * 
 */
package com.ajira.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author raagul.s
 *
 */
public class DeviceRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6809505160176691654L;
	
	private String type;
	private String name;
	private String source;
	private List<String> targets;
	private String value;
	
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

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the targets
	 */
	public List<String> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(List<String> targets) {
		this.targets = targets;
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
