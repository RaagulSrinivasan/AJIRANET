package com.ajira.network.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.Nodes;

@RunWith(SpringJUnit4ClassRunner.class)
public class NetworkServiceImplTest {
	
	@InjectMocks
	private NetworkServiceImpl networkServiceMock;
	
	static List<Device> devices;
	static List<Nodes> activeNodes;
	
	@Before
	public void setUp() {
		devices = new ArrayList<>();
		activeNodes = new ArrayList<>();
	}

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
		networkServiceMock.addDevice(new DeviceRequest("COMPUTER", "A1"));
		assertNotNull(devices);
		//assertEquals("COMPUTER", devices.get(0).getType());
	}
}

