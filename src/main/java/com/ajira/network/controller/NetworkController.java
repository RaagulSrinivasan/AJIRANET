package com.ajira.network.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ajira.network.constants.CommandType;
import com.ajira.network.exception.DeviceNotFoundException;
import com.ajira.network.exception.ValidationException;
import com.ajira.network.model.Device;
import com.ajira.network.model.DeviceRequest;
import com.ajira.network.model.DeviceResponse;
import com.ajira.network.model.Response;
import com.ajira.network.service.NetworkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class NetworkController {

	@Autowired
	private NetworkService networkService;

	@PostMapping(value = "/ajiranet/process")
	public ResponseEntity<?> processNetwork(@RequestBody String request, HttpServletResponse httpResponse)
			throws IOException {

		Response response = null;
		String requestJson = null;
		DeviceRequest deviceRequest = null;

		// retrieving commandType, command and JSON requestBody if available from
		// request
		String[] requestBody = request.split("\n\\s*\n");
		String[] commandAndHeaders = requestBody[0].split("\n");

		if (requestBody.length > 1) {
			requestJson = requestBody[1];
		}
		
		String[] commands = commandAndHeaders[0].split(" ");

		String commandType = commands[0];
		String command = commands[1];

		// Check if command type is one of CREATE, MODIFY and FETCH
 		if (!Arrays.stream(CommandType.values()).anyMatch(c -> c.getName().equals(commandType))) {
			response = new Response("Invalid Command Type");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}

		// Check if any headers provided in request
		List<String> headers = new ArrayList<>();
		if (commandAndHeaders.length > 1) {
			headers = new ArrayList<String>();
			for (int i = 1; i < commandAndHeaders.length; i++) {
				headers.add(commandAndHeaders[i]);
			}
		}

		// Check for content Type
		String contentType = null;
		for (String header : headers) {
			if (header.contains("content-type")) {
				contentType = header.split(":")[1].trim();
			}
		}
		
		// is request body is available without content-type, throw bad request
		if (null != requestJson) {
			if (null == contentType) {
				response = new Response("Invalid format");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}

			// if content-type application JSON, cast it to Device Request
			if (contentType.equals("application/json")) {

				ObjectMapper mapper = new ObjectMapper();
				try {
					deviceRequest = mapper.readValue(requestJson, DeviceRequest.class);
				} catch (JsonProcessingException e) {
					response = new Response("Invalid format");
					return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
				}
			}
		}

		switch (commandType) {
		case "CREATE":

			if (command.equals("/devices") && null != deviceRequest) {
				return addDevice(deviceRequest);
			} else if (command.equals("/connections") && null != deviceRequest) {
				return createConnections(deviceRequest);
			} else {
				response = new Response("Invalid Command");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

		case "MODIFY":

			String[] modifyParams = command.split("/");
			if (modifyParams[1].equals("devices") && modifyParams[3].equals("strength")) {
				return modifyStrength(deviceRequest, modifyParams[2]);
			}

		case "FETCH":

			if (command.equals("/devices")) {
				return fetchDevice();
			} else if (command.contains("/info-routes")) {
				httpResponse.sendRedirect(command);
			}

		}

		return null;
	}

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

	public ResponseEntity<DeviceResponse> fetchDevice() {

		DeviceResponse deviceResponse = null;

		List<Device> devices = networkService.fetchAllDevices();
		deviceResponse = new DeviceResponse(devices);

		return new ResponseEntity<DeviceResponse>(deviceResponse, HttpStatus.OK);
	}

	public ResponseEntity<Response> createConnections(DeviceRequest connectionRequest) {

		Response responseMessage = null;

		try {
			String response = networkService.establishConnection(connectionRequest);
			responseMessage = new Response(response);
		} catch (ValidationException ex) {
			responseMessage = new Response(ex.getErrorMessage());
			return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	}

	public ResponseEntity<Response> modifyStrength(DeviceRequest strength, String device) {

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

	@GetMapping(value = "/info-routes")
	public ResponseEntity<Response> fetchRoutes(@RequestParam String from, @RequestParam String to) {

		Response responseMessage = null;
		try {
			String response = networkService.fetchRoutes(from, to);
			responseMessage = new Response(response);
		} catch (ValidationException ex) {
			responseMessage = new Response(ex.getErrorMessage());
			return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		} catch (DeviceNotFoundException ex) {
			responseMessage = new Response(ex.getErrorMessage());
			return new ResponseEntity<Response>(responseMessage, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Response>(responseMessage, HttpStatus.OK);
	}

}
