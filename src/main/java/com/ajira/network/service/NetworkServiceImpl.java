package com.ajira.network.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ajira.network.constants.Constants;
import com.ajira.network.constants.DeviceType;
import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.Nodes;

@Service
public class NetworkServiceImpl implements NetworkService {

	//Persistance for devices
	public static List<Device> devices = new ArrayList<>();
	public static List<Nodes> activeNodes = new ArrayList<>();

	@Override
	public String addDevice(DeviceRequest deviceRequest) throws ValidationException {

		String deviceType = deviceRequest.getType();
		String deviceName = deviceRequest.getName();
		
		if (StringUtils.isEmpty(deviceName)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Device name not available");
		}

		//Check if device type is available
		if(StringUtils.isEmpty(deviceType)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE,
					"Device type not available");
		}
		// Check for device type
		if (deviceRequest.getType().equals(DeviceType.COMPUTER.toString())
				|| deviceRequest.getType().equals(DeviceType.REPEATER.toString())) {

			Device device = null;
			
			// if new device, add to devices
			// if existing devices, don't add
			if (!CollectionUtils.isEmpty(devices)) {
				boolean isExistingDevice = devices.stream().anyMatch(d -> d.getName().equals(deviceRequest.getName()));
				if (isExistingDevice) {
					throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE,
							"Device '" + deviceRequest.getName() + "' already exists");
				} else {
					device = new Device(deviceRequest.getType(), deviceRequest.getName(), Constants.DEFAULT_STRENGTH);
					devices.add(device);
				}
			} else {
				device = new Device(deviceRequest.getType(), deviceRequest.getName(), Constants.DEFAULT_STRENGTH);
				devices.add(device);
			}
		} else {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE,
					"type '" + deviceRequest.getType() + "' is not supported");
		}
		return "Successfully added " + deviceRequest.getName();
	}

	@Override
	public List<Device> fetchAllDevices() {
		return devices;
	}

	@Override
	public String establishConnection(DeviceRequest connectionRequest) throws ValidationException {

		String source = connectionRequest.getSource();
		List<String> targets = connectionRequest.getTargets();

		// Both source and target is mandatory to establish a connection
		if (null == source || CollectionUtils.isEmpty(targets)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Invalid command syntax");
		}
		
		List<String> availableDevices = fetchAvailableDevices();
		
		// Check if source is already configured
		boolean isSourceAvailable = availableDevices.stream().anyMatch(device -> device.equals(source));
		if (!isSourceAvailable) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Node '" + source + "' not found");
		}

		// Check if all targets are already configured
		boolean isTargetAvailable = availableDevices.containsAll(targets);
		if (!isTargetAvailable) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "All targets are not found");
		}
		
		//source and target device should not be the same
		if (targets.stream().anyMatch(target -> target.equals(source))) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Cannot connect to device itself");
		}

		if (!CollectionUtils.isEmpty(activeNodes)) {

			Optional<Nodes> sourceNode = fetchNode(source);

			boolean isConnectionAvailable = false;

			// Check if source and target are already connected.
			if (sourceNode.isPresent()) {
				for (String target : targets) {
					if (sourceNode.get().getChild().contains(target)) {
						isConnectionAvailable = true;
						break;
					}
				}

				// if all targets are not connected before, connect to the corresponding source
				if (isConnectionAvailable) {
					throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Device  are already connected");
				} else {
					sourceNode.get().getChild().addAll(targets);
				}
				
			} else {
				Nodes newNode = new Nodes(source, targets);
				activeNodes.add(newNode);
			}
			
		} else {

			Nodes newNode = new Nodes(source, targets);
			activeNodes.add(newNode);
		}

		return "Successfully connected";
	}

	@Override
	public String modifyDeviceStrength(DeviceRequest strength, String device) throws DeviceNotFoundException, ValidationException {

		if ((null != strength) && !StringUtils.isEmpty(device)) {
			
			List<String> availableDevices = fetchAvailableDevices();
			
			// Check if input device is available, if available add strength
			if (!availableDevices.contains(device)) {
				throw new DeviceNotFoundException(Constants.NOT_FOUND_ERR_CODE, "Device not found");
			} else {
				Integer strengthValue;
				try {
					 strengthValue = Integer.valueOf(strength.getValue());
				} catch (NumberFormatException ex) {
					throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Value should be an integer");
				}
				for (Device deviceInfo : devices) {
					if (deviceInfo.getName().equals(device)) {
						deviceInfo.setStrength(strengthValue);
						break;
					}
				}
			}
		}
		return "Successfully defined strength";
	}
	
	private List<String> fetchAvailableDevices(){
		return devices.stream().map(Device::getName).collect(Collectors.toList());
	}
	
	private Optional<Nodes> fetchNode(String source) {
		return activeNodes.stream().filter(node -> node.getParent().equals(source)).findFirst();
	}

	@Override
	public String fetchRoutes(String source, String destination) throws ValidationException, DeviceNotFoundException {

		//Source and destination is mandatory to fetch route
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(destination)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Invalid Request");
		}
		
		List<String> availableDevices = fetchAvailableDevices();
		
		//Route cannot be calculated for repeater
		for(Device device: devices) {
			if(device.getName().equals(source) || device.getName().equals(destination)) {
				if(device.getType().equals(DeviceType.REPEATER.toString())){
				throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Route cannot be calculated with repeater");
				}
			}
		}

		//Source and destination should be already configured
		if (!availableDevices.contains(source)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Node '" + source + "' not found");
		} else if (!availableDevices.contains(destination)) {
			throw new ValidationException(Constants.BAD_REQUEST_ERR_CODE, "Node '" + destination + "' not found");
		}

		List<String> visited = new LinkedList<>();
		Stack<String> stack = new Stack<String>();
		List<String> result = new LinkedList<>();

		if (source.equals(destination)) {
			return "Route is " + source + "->" + destination;
		}

		//Add first device by default
		stack.push(source);
		visited.add(source);
		
		//Stop when all adjacent nodes have been visited
		while (!stack.isEmpty()) {

			String current = stack.pop();
			result.add(current);
			visited.add(current);

			Optional<Nodes> nodesWithAdjacent = fetchNode(current);

			if (fetchNode(current).isPresent()) {

				for (String child : nodesWithAdjacent.get().getChild()) {

					// if destination is found, exit
					if (child.equals(destination)) {
						result.add(child);
						stack.clear();
						break;

					} else {
						Optional<Nodes> childWithAdjacent = fetchNode(child);
						
						//If the adjacent node is not the end node, add it to stack
						if (childWithAdjacent.isPresent()) {

							//Only non visited nodes to avoid repetition
							if (!visited.contains(child)) {
								stack.push(child);
								visited.add(child);
							}
						}
					}
				}
			}
		}
		
		StringJoiner joiner = new StringJoiner("->");

		// Check if last device is destination device
		if (destination.equals(result.get(result.size() - 1))) {
			for (String route : result) {
				joiner.add(route);
			}
		}

		if (joiner.length() == 0) {
			throw new DeviceNotFoundException(Constants.NOT_FOUND_ERR_CODE, "Route not found");
		}
		

		return "Route is "+joiner.toString();
	}
}

