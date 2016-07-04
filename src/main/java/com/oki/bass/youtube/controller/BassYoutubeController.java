package com.oki.bass.youtube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oki.bass.youtube.service.BassYoutubeService;
import com.oki.bass.youtube.vo.BassYoutubeContentVO;

@RestController
@RequestMapping(value = "/youtube")
public class BassYoutubeController {
	
	@Autowired
	private BassYoutubeService bassYoutubeService;
	
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public BassYoutubeContentVO youtube(@PathVariable int page) {
		return bassYoutubeService.youtube(page);
	}
	
}
