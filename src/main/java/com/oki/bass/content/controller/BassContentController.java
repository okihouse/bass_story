package com.oki.bass.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oki.bass.content.service.BassContentService;
import com.oki.bass.content.vo.detail.BassContentVO;
import com.oki.error.exception.BassProcessException;

@RestController
@RequestMapping(value = "/content")
public class BassContentController {

	@Autowired
	private BassContentService bassContentService;
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	@ResponseBody
	public BassContentVO content(@PathVariable int page) throws BassProcessException{
		return bassContentService.content(page);
	}
	
}
