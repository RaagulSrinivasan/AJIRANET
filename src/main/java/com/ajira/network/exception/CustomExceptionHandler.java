package com.ajira.network.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ajira.network.model.Response;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler({ InvalidFormatException.class })
	public ResponseEntity<Response> handleMethodArgumentTypeMismatch(InvalidFormatException ex) {

		Response responseMessage = null;
		String error = "value should be an " + ex.getTargetType().getSimpleName();
		responseMessage = new Response(error);

		return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public ResponseEntity<Response> HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {

		Response responseMessage = null;
		String error = "Invalid command syntax";
		responseMessage = new Response(error);

		return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Response> MissingServletRequestParameterException(MissingServletRequestParameterException ex) {

		Response responseMessage = null;
		String error = "Invalid Request";
		responseMessage = new Response(error);

		return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
	}
	
	
}
