package com.ajira.network.model;

import java.util.List;

public class Nodes {
	
	private String parent;
	private List<String> child;
	
	/**
	 * @param parent
	 * @param child
	 */
	public Nodes(String parent, List<String> child) {
		this.parent = parent;
		this.child = child;
	}
	
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	/**
	 * @return the child
	 */
	public List<String> getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(List<String> child) {
		this.child = child;
	}
	
	

}
