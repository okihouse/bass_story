package com.oki.error.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.oki.config.log.BassLogManager;

public class BassBindingException extends BassException {

	private static final Logger logger = LoggerFactory.getLogger(BassBindingException.class);
	
	private static final long serialVersionUID = -4409954855381406454L;
	private static final String MESSAGE_DELEMETER = "#";
	
	private int code;
	private String message;
	
	public BassBindingException(BindingResult bindingResult) {
		FieldError fieldError = bindingResult.getFieldError();
		String errorMessage = "";
		int errorCode = -1;
		try {
			if (fieldError.getCode().equals("Pattern")) {
				errorCode =Integer.valueOf(fieldError.getDefaultMessage().split(MESSAGE_DELEMETER)[0]);
				errorMessage = String.format(fieldError.getDefaultMessage().split(MESSAGE_DELEMETER)[1], fieldError.getField(), fieldError.getRejectedValue());
			} else {
				errorCode =Integer.valueOf(fieldError.getDefaultMessage().split(MESSAGE_DELEMETER)[0]);
				errorMessage = String.format(fieldError.getDefaultMessage().split(MESSAGE_DELEMETER)[1], fieldError.getField());
			}
		} catch (Exception e) {
			errorMessage = fieldError.getDefaultMessage();
			logger.warn(BassLogManager.makeLog(fieldError.getDefaultMessage(), e));
		}
		this.message = errorMessage;
		this.code = errorCode;
	}

	
	@Override
	public int errorCode() {
		return code;
	}

	@Override
	public String errorMessage() {
		return message;
	}

}
