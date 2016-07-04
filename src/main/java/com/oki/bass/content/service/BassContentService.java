package com.oki.bass.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.oki.bass.content.domain.entity.Content;
import com.oki.bass.content.domain.repository.BassContentRepository;
import com.oki.bass.content.vo.detail.BassContentVO;
import com.oki.error.exception.BassProcessException;

@Service
public class BassContentService {

	//private static final Logger logger = LoggerFactory.getLogger(BassContentService.class);
	
	@Autowired
	private BassContentRepository bassContentRepository;
	
	public BassContentVO content(int page) throws BassProcessException {
		Pageable pageRequest = new PageRequest(page - 1, 30, new Sort(Direction.DESC, "registerDate"));
		
		Page<Content> pages = bassContentRepository.findAll(pageRequest);
		
//		int size = pages.getContent().size();
//		if (page == 1 && size > 3) {
//			List<Content> carousel = pages.getContent().subList(0, 3);
//			List<Content> contents = pages.getContent().subList(3, size);
//			bassContentVO.setCarousel(carousel);
//			bassContentVO.setContents(contents);
//		} else {
//			bassContentVO.setContents(pages.getContent());
//		}
		
		BassContentVO bassContentVO = new BassContentVO();
		bassContentVO.setContents(pages.getContent());
		bassContentVO.setLast(pages.isLast());
		return bassContentVO;
	}

}
