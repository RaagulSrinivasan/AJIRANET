/**
 * 
 */
package com.ajira.network.service;

import java.util.List;

import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;

/**
 * @author raagul.s
 *
 */
public interface NetworkService {
	/**
	 * Add a new device to the network, throw validation exception if device already exists
	 * @param deviceRequest
	 * @return responseMessage
	 * @throws ValidationException
	 */
	public String addDevice(DeviceRequest deviceRequest) throws ValidationException;
	
	/**
	 * Fetch all available device information (name and type)
	 * @return Device
	 */
	public List<Device> fetchAllDevices();
	
	/**
	 * Establish connection between source and target nodes
	 * @param connectionRequest
	 * @return responseMessage
	 * @throws ValidationException
	 */
	public String establishConnection(DeviceRequest connectionRequest) throws ValidationException;
	
	/**
	 * Add signal strength to the device
	 * @param strength
	 * @param device
	 * @return responseMessage
	 * @throws DeviceNotFoundException
	 * @throws ValidationException
	 */
	public String modifyDeviceStrength(DeviceRequest strength, String device)throws DeviceNotFoundException, ValidationException;
	
	/**
	 * fetches the route between the source and destination nodes
	 * @param source
	 * @param destination
	 * @return route
	 * @throws ValidationException
	 * @throws DeviceNotFoundException
	 */
	public String fetchRoutes(String source, String destination)throws ValidationException, DeviceNotFoundException;

}
