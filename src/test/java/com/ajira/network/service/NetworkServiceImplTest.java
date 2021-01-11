package com.ajira.network.service;

import static com.ajira.network.service.NetworkServiceImpl.devices;
import static com.ajira.network.service.NetworkServiceImpl.activeNodes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.ajira.network.constants.Constants;
import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.Nodes;

@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceImplTest {
	
	@InjectMocks
	private NetworkServiceImpl networkServiceMock;
	
	@Test(expected=ValidationException.class)
	public void testAddDeviceWithEmptyDeviceName() throws Exception {
		networkServiceMock.addDevice(new DeviceRequest("COMPUTER", ""));
	}
	
	@Test(expected=ValidationException.class)
	public void testAddDeviceWithNullDeviceName() throws Exception {
		networkServiceMock.addDevice(new DeviceRequest("COMPUTER", null));
	}

	@Test(expected=ValidationException.class)
	public void testAddDeviceWithEmptyDeviceType() throws Exception {
		networkServiceMock.addDevice(new DeviceRequest("", "A1"));
	}

	@Test(expected=ValidationException.class)
	public void testAddDeviceWithInvalidDeviceType() throws Exception {
		networkServiceMock.addDevice(new DeviceRequest("PHONE", "A1"));
	}
	
	@Test()
	public void testAddDeviceWithValidDeviceType() throws Exception {
		devices = new ArrayList<Device>();
		String response = networkServiceMock.addDevice(new DeviceRequest("COMPUTER", "A1"));
		assertNotNull(devices);
		
		devices.forEach(device -> {
			if(device.getName().equals("A1")) {
				assertEquals("COMPUTER", device.getType());
				assertEquals("A1", device.getName());
			}
		});
		
		assertEquals("Successfully added A1", response);
		
	}
	
	@Test()
	public void testAddDeviceWithExistingDevice() throws Exception {
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));

		String response = networkServiceMock.addDevice(new DeviceRequest("COMPUTER", "A2"));
		
		assertEquals("Successfully added A2", response);
	}
	
	@Test(expected=ValidationException.class)
	public void testAddDeviceWithNewWithExistingDevice() throws Exception {
		
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.addDevice(new DeviceRequest("COMPUTER", "A1"));
				
	}
	
	@Test()
	public void testAddDeviceWithDeviceTypeRepeater() throws Exception {
		devices = new ArrayList<Device>();
		String response = networkServiceMock.addDevice(new DeviceRequest("REPEATER", "R1"));
		assertNotNull(devices);
		
		devices.forEach(device -> {
			if(device.getName().equals("R1")) {
				assertEquals("REPEATER", device.getType());
				assertEquals("R1", device.getName());
			}
		});
		
		assertEquals("Successfully added R1", response);
		
		
	}
	
	@Test()
	public void testFetchAllDevices() throws Exception {
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.fetchAllDevices();
		assertNotNull(devices);
	}
	
	@Test(expected = ValidationException.class)
	public void testEstablishConnectionWithNullSource() throws Exception {
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource(null);
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A1")));
		
		networkServiceMock.establishConnection(connectionRequest);
	}
	
	@Test(expected = ValidationException.class)
	public void testEstablishConnectionWithEmptySource() throws Exception {
		
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));

		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A1")));
		
		networkServiceMock.establishConnection(connectionRequest);
	}
	
	@Test(expected = ValidationException.class)
	public void testEstablishConnectionWithEmptyTarget() throws Exception {
		
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));

		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A1");
		connectionRequest.setTargets(new ArrayList<String>());
		
		networkServiceMock.establishConnection(connectionRequest);
	}
	
	@Test(expected = ValidationException.class)
	public void testEstablishConnectionWithInvalidSource() throws Exception {
		
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A10");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A1")));
		
		networkServiceMock.establishConnection(connectionRequest);
	}
	
	@Test(expected = ValidationException.class)
	public void testEstablishConnectionWithInvalidTarget() throws Exception {
		
		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A10", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A10");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A1")));
		
		networkServiceMock.establishConnection(connectionRequest);
	}
	
	
	@Test()
	public void testEstablishConnectionWithValidSourceAndTarget() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A1");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A2")));
		
		String response = networkServiceMock.establishConnection(connectionRequest);
		
		assertNotNull(NetworkServiceImpl.activeNodes);
		assertEquals("Successfully connected", response);
	}
	
	@Test()
	public void testEstablishConnectionMultipleTarget() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A3", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A1");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A2", "A3")));
		
		String response = networkServiceMock.establishConnection(connectionRequest);
		
		assertNotNull(NetworkServiceImpl.activeNodes);
		assertEquals("Successfully connected", response);
	}
	
	@Test()
	public void testEstablishConnectionAddToExistingSource() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A3", Constants.DEFAULT_STRENGTH));
		
		activeNodes.add(new Nodes("A1", new ArrayList<String>(Arrays.asList("A2"))));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setSource("A1");
		connectionRequest.setTargets(new ArrayList<String>(Arrays.asList("A3")));
		
		String response = networkServiceMock.establishConnection(connectionRequest);
		
		assertNotNull(activeNodes);
		assertEquals("Successfully connected", response);
	}
	
	@Test
	public void testModifyDeviceStrengthWithValidStrength() throws Exception {

		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setValue("2");
		
		String response = networkServiceMock.modifyDeviceStrength(connectionRequest, "A1");
		
		assertNotNull(NetworkServiceImpl.activeNodes);
		assertEquals("Successfully defined strength", response);
		 
	}
	
	@Test(expected = ValidationException.class)
	public void testModifyDeviceStrengthWithStringInput() throws Exception {

		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setValue("Hello World");
		
		networkServiceMock.modifyDeviceStrength(connectionRequest, "A1");
		 
	}
	
	
	@Test(expected = DeviceNotFoundException.class)
	public void testModifyDeviceStrengthWithInvalidDevice() throws Exception {

		devices = new ArrayList<Device>();
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		
		DeviceRequest connectionRequest = new DeviceRequest();
		connectionRequest.setValue("Hello World");
		
		networkServiceMock.modifyDeviceStrength(connectionRequest, "A2");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithEmptySource() throws Exception {
		networkServiceMock.fetchRoutes("", "A2");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithEmptyDestination() throws Exception {
		networkServiceMock.fetchRoutes("A1", "");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithRepeaterAsSource() throws Exception {
		
		devices = new ArrayList<Device>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("REPEATER", "R1", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.fetchRoutes("R1", "A1");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithRepeaterAsDestination() throws Exception {
		
		devices = new ArrayList<Device>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("REPEATER", "R1", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.fetchRoutes("A1", "R1");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithSourceNotAvailable() throws Exception {
		
		devices = new ArrayList<Device>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.fetchRoutes("A2", "A5");
	}
	
	@Test(expected = ValidationException.class)
	public void testFetchRoutesWithDestinationNotAvailable() throws Exception {
		
		devices = new ArrayList<Device>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		networkServiceMock.fetchRoutes("A1", "A2");
	}
	
	@Test()
	public void testFetchRoutesWithSameSourceDestination() throws Exception {
		
		devices = new ArrayList<Device>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		String response = networkServiceMock.fetchRoutes("A1", "A1");
		
		assertEquals("Route is A1->A1", response);
	}
	
	@Test()
	public void testFetchRoutesWithValidRoute() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		activeNodes.add(new Nodes("A1", new ArrayList<String>(Arrays.asList("A5", "A2"))));
		activeNodes.add(new Nodes("A2", new ArrayList<String>(Arrays.asList("A5"))));

		
		String response = networkServiceMock.fetchRoutes("A1", "A5");
		
		assertEquals("Route is A1->A5", response);
	}
	
	@Test(expected = DeviceNotFoundException.class)
	public void testFetchRoutesWithInValidRoute() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		activeNodes.add(new Nodes("A1", new ArrayList<String>(Arrays.asList("A2"))));
		activeNodes.add(new Nodes("A1", new ArrayList<String>(Arrays.asList("A5"))));
		
		networkServiceMock.fetchRoutes("A2", "A5");
	}
	
	@Test()
	public void testFetchRoutesWithValidRoute_2() throws Exception {
		
		devices = new ArrayList<Device>();
		activeNodes = new ArrayList<Nodes>();
		
		devices.add(new Device("COMPUTER", "A1", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A2", Constants.DEFAULT_STRENGTH));
		devices.add(new Device("COMPUTER", "A5", Constants.DEFAULT_STRENGTH));
		
		activeNodes.add(new Nodes("A1", new ArrayList<String>(Arrays.asList("A2"))));
		activeNodes.add(new Nodes("A2", new ArrayList<String>(Arrays.asList("A5"))));

		
		String response = networkServiceMock.fetchRoutes("A1", "A5");
		
		assertEquals("Route is A1->A2->A5", response);
	}
	
	
	
	
	
	
	
	
	
	
	
}

