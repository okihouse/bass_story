package com.oki.error.exception;

import com.oki.error.vo.ErrorVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BassProcessException extends BassException {

	private static final long serialVersionUID = 6662725571906600991L;

	private int code;
	private String message;
	
	public BassProcessException(ErrorVO errorVO) {
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
