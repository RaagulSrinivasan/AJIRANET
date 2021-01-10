package com.ajira.network.model;

import java.io.Serializable;
import java.util.List;

public class ConnectionRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7367346624198110847L;
	
	private String source;
	private List<String> targets;
	
	/**
	 * 
	 */
	public ConnectionRequest() {
	}
	/**
	 * @param source
	 * @param targets
	 */
	public ConnectionRequest(String source, List<String> targets) {
		this.source = source;
		this.targets = targets;
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
	
	

}
