package com.oki.error;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oki.config.log.BassLogConstant;
import com.oki.config.log.BassLogManager;
import com.oki.config.property.PropertiesResource;
import com.oki.config.vo.BassRequestDataVO;
import com.oki.error.constant.BassErrorConstant;
import com.oki.error.exception.BassBindingException;
import com.oki.error.exception.BassParamException;
import com.oki.error.exception.BassProcessException;
import com.oki.error.notification.BassErrorNotificateSlack;
import com.oki.error.vo.ErrorVO;

@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@Autowired
	private BassRequestDataVO bassRequestDataVO;
	
	@Autowired
	private PropertiesResource propertiesResource;
	
	@Autowired
	private BassErrorNotificateSlack dnbbErrorNotificateSlack;
	
	@ExceptionHandler
	@ResponseBody
	public ErrorVO handlerException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		ErrorVO errorVO = new ErrorVO();
		if (exception instanceof BassBindingException) {
			BassBindingException dnbbBindingException = (BassBindingException) exception;
			errorVO.setErrorCode(dnbbBindingException.errorCode());
			errorVO.setErrorMessage(dnbbBindingException.errorMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof BassParamException) {
			BassParamException dnbbParamException = (BassParamException) exception;
			errorVO.setErrorCode(dnbbParamException.errorCode());
			errorVO.setErrorMessage(dnbbParamException.errorMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof BassProcessException) {
			BassProcessException dnbbProcessException = (BassProcessException) exception;
			errorVO.setErrorCode(dnbbProcessException.errorCode());
			errorVO.setErrorMessage(dnbbProcessException.errorMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof MissingServletRequestParameterException) {
			errorVO = propertiesResource.getErrorValue(BassErrorConstant.MISSING_PARAMETER_ERROR_KEY, ((MissingServletRequestParameterException) exception).getParameterName());
			errorVO.setErrorMessage((String.format(errorVO.getErrorMessage(), ((MissingServletRequestParameterException) exception).getParameterName())));
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof HttpRequestMethodNotSupportedException) { 
			errorVO = propertiesResource.getErrorValue(BassErrorConstant.NOT_SUPPORT_ERROR_KEY);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof HttpMediaTypeNotSupportedException) { 
			errorVO = propertiesResource.getErrorValue(BassErrorConstant.UNSUPPORT_MEDIA_TYPE_ERROR_KEY);
			errorVO.setErrorMessage(String.format(errorVO.getErrorMessage(), ((HttpMediaTypeNotSupportedException) exception).getContentType()));
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else if (exception instanceof HttpMessageNotReadableException) { 
			errorVO = propertiesResource.getErrorValue(BassErrorConstant.NOT_ACCEPTABLE_ERROR_KEY);
			errorVO.setErrorMessage(String.format(errorVO.getErrorMessage(), ((HttpMessageNotReadableException) exception).getMessage()));
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		} else {
			exception.printStackTrace();
			logger.error("{}| error={}", bassRequestDataVO.getTid(), BassLogManager.makeLog(exception.getMessage(), exception));
			errorVO = propertiesResource.getErrorValue(BassErrorConstant.UNKNOWN_ERROR_KEY);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			dnbbErrorNotificateSlack.send(exception, convertStreamToString(request));
		}
		
		long elapsed = (System.currentTimeMillis() - bassRequestDataVO.getApiStartTime());
		String time = elapsed > 5000 ? elapsed + "ms, TOOLONG" :  elapsed + "ms";
		
		logger.error(BassLogManager.makeLog(4 ,BassLogConstant.MODULE_NAME ,bassRequestDataVO.getTid() ,BassLogConstant.IP_ADDRESS ,BassLogConstant.RES
				 ,BassLogConstant.ELAPSED_TIME, time
			     ,BassLogConstant.RETURN_ERROR_CODE ,errorVO.getErrorCode()
			     ,BassLogConstant.RETURN_ERROR_MSG ,errorVO.getErrorMessage()
			     ));
		
		return errorVO;
	}
	
	private String convertStreamToString(HttpServletRequest request){
		String value = "";
		try {
			value = StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
