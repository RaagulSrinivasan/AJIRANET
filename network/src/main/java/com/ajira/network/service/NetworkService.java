/**
 * 
 */
package com.ajira.network.service;

import java.util.List;

import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.ConnectionRequest;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.StrengthRequest;

/**
 * @author raagul.s
 *
 */
public interface NetworkService {
	
	public String addDevice(DeviceRequest deviceRequest) throws ValidationException;
	
	public List<Device> fetchAllDevices();
	
	public String establishConnection(ConnectionRequest connectionRequest) throws ValidationException;
	
	public String modifyDeviceStrength(StrengthRequest strength, String device)throws DeviceNotFoundException, ValidationException;
	
	public String fetchRoutes(String source, String destination)throws ValidationException;

}
