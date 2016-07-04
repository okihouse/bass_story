package com.oki.bass.info.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oki.bass.info.service.BassInfoService;
import com.oki.bass.info.vo.BassVersionInsertVO;
import com.oki.bass.info.vo.BassVersionVO;
import com.oki.config.vo.SuccessVO;
import com.oki.error.exception.BassBindingException;

@RestController
@RequestMapping(value = "/info")
public class BassInfoController {
	
	@Autowired
	private BassInfoService bassInfoService;
	
	// version
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public BassVersionVO version() {
		return bassInfoService.version();
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public SuccessVO insert(@RequestBody @Valid BassVersionInsertVO bassVersionInsertVO, BindingResult bindingResult) throws BassBindingException {
		if (bindingResult.hasErrors()) {
			throw new BassBindingException(bindingResult);
		}
		return bassInfoService.insert(bassVersionInsertVO.getVersion());
	}
	
}
