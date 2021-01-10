package com.ajira.network.model;

import java.io.Serializable;
import java.util.List;

public class DeviceResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6330773510266875621L;
	
	List<Device> devices;
	
	/**
	 * 
	 */
	public DeviceResponse() {
		super();
	}


	/**
	 * @param devices
	 */
	public DeviceResponse(List<Device> devices) {
		this.devices = devices;
	}


	/**
	 * @return the devices
	 */
	public List<Device> getDevices() {
		return devices;
	}


	/**
	 * @param devices the devices to set
	 */
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	

}
