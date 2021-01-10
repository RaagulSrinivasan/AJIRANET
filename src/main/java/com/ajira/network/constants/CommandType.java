package com.ajira.network.constants;

public enum CommandType {
	
	CREATE("CREATE"), MODIFY("MODIFY"), FETCH("FETCH");
	
	private String name;
	
	/**
	 * 
	 */
	private CommandType(String name) {
		this.name=name;
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
