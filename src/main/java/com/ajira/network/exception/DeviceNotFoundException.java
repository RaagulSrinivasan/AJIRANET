package com.ajira.network.exception;

public class DeviceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5708012019090980609L;
	
	private String errorCode;
	private String errorMessage;
	
	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public DeviceNotFoundException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
