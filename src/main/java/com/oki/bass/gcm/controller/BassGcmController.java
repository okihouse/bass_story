package com.oki.bass.gcm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oki.bass.gcm.service.BassGcmService;
import com.oki.bass.gcm.vo.BassGcmInsertVO;
import com.oki.config.vo.SuccessVO;
import com.oki.error.exception.BassBindingException;

@RestController
@RequestMapping(value = "/gcm")
public class BassGcmController {

	@Autowired
	private BassGcmService bassGcmService;
	
	// GCM 입력
	@RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public SuccessVO insert(@RequestBody @Valid BassGcmInsertVO bassGcmInsertVO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			throw new BassBindingException(bindingResult);
		}
		bassGcmService.insert(bassGcmInsertVO);
		return new SuccessVO();
	}
	
}
