package com.oki.error.exception;

import com.oki.error.vo.ErrorVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BassParamException extends BassException {

	private static final long serialVersionUID = -1824596885248560072L;

	private int code;
	private String message;
	
	public BassParamException(ErrorVO errorVO) {
		this.code = errorVO.getErrorCode();
		this.message = errorVO.getErrorMessage();
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
