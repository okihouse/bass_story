package com.oki.bass.youtube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.oki.bass.type.BassType;
import com.oki.bass.youtube.domain.repository.BassYoutubeRepository;
import com.oki.bass.youtube.vo.BassYoutubeContentVO;
import com.oki.bass.youtube.vo.BassYoutubeContentVO.BassYoutubeContents;

@Service
public class BassYoutubeService {
	
//	private static final Logger logger = LoggerFactory.getLogger(BassYoutubeService.class);

	@Autowired
	private BassYoutubeRepository bassYoutubeRepository;
	
	public BassYoutubeContentVO youtube(int page) {
		Pageable pageable = new PageRequest(page - 1, 30, new Sort(Direction.DESC, "publishedDate"));
		
		Page<BassYoutubeContents> pages = bassYoutubeRepository.findByView(BassType.YN.Y ,pageable);
		
		BassYoutubeContentVO bassYoutubeContentVO = new BassYoutubeContentVO();
		bassYoutubeContentVO.setYoutubes(pages.getContent());
		bassYoutubeContentVO.setLast(pages.isLast());
		return bassYoutubeContentVO;
	}

}
