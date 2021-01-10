package com.ajira.network.model;

import java.io.Serializable;

public class Response implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6491835789751724404L;
	
	String msg;
	
	/**
	 * @param msg
	 */
	public Response(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
