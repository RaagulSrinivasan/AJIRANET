package com.ajira.network.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.ConnectionRequest;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.DeviceResponse;
import com.ajira.network.model.Response;
import com.ajira.network.model.StrengthRequest;
import com.ajira.network.service.NetworkService;

@RestController
@RequestMapping(value = "/ajiranet/process")
public class NetworkController {
	
	@Autowired
	private NetworkService networkService;
	
	@PostMapping(value = "/CREATE/devices",consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addDevice(@RequestBody DeviceRequest deviceRequest) {
		
		Response responseMessage = null;
		try {
			String response = networkService.addDevice(deviceRequest);
			responseMessage = new Response(response);
			} catch (ValidationException ex) {
				responseMessage = new Response(ex.getErrorMessage());
				return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	} 
	
	
	@GetMapping(value = "/FETCH/devices")
	public ResponseEntity<DeviceResponse> fetchDevice() {
		
		DeviceResponse deviceResponse = null;
		
			List<Device> devices = networkService.fetchAllDevices();
			deviceResponse = new DeviceResponse(devices);
			
		return new ResponseEntity<DeviceResponse>(deviceResponse, HttpStatus.OK);
	} 
	
	
	@PostMapping(value = "/CREATE/connections",consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createConnections(@RequestBody ConnectionRequest connectionRequest) {
		
		Response responseMessage = null;
		
			try {
			String response = networkService.establishConnection(connectionRequest);
			responseMessage = new Response(response);
			}
			catch(ValidationException ex) {
				responseMessage = new Response(ex.getErrorMessage());
				return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
			}
			
		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	} 
	
	@PostMapping(value = "/MODIFY/devices/{device}/strength",consumes = MediaType.APPLICATION_JSON_VALUE, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> modifyStrength(@RequestBody StrengthRequest strength, @PathVariable String device) {
		
		Response responseMessage = null;
		
			try {
				String response = networkService.modifyDeviceStrength(strength, device);
				responseMessage = new Response(response);
			} catch (DeviceNotFoundException ex) {
				responseMessage = new Response(ex.getErrorMessage());
				return new ResponseEntity<Response>(responseMessage, HttpStatus.NOT_FOUND);
			} catch (ValidationException ex) {
				responseMessage = new Response(ex.getErrorMessage());
				return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
			} 
			
		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	} 
	
	@GetMapping(value = "/FETCH/info-routes")
	public ResponseEntity<Response> fetchroutes(@RequestParam String from, @RequestParam  String to) {
		
		Response responseMessage = null;
			try {
				String response = networkService.fetchRoutes(from , to);
				responseMessage = new Response(response);
			} catch (ValidationException ex) {
				responseMessage = new Response(ex.getErrorMessage());
				return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
			}
			
		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	} 

}
