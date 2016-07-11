package com.oki.bass.info.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oki.bass.info.domain.entity.Information;
import com.oki.bass.info.domain.repository.BassInformationRepository;
import com.oki.bass.info.vo.BassVersionVO;
import com.oki.config.vo.SuccessVO;

@Service
public class BassInfoService {

	@Autowired
	private BassInformationRepository bassInfomationRepository;
	
	public BassVersionVO version() {
		Information infomation = bassInfomationRepository.findFirstByOrderByIssueDateDesc();
		if (infomation == null) {
			infomation = new Information();
		}
		return new BassVersionVO(infomation.getVersion(), infomation.getIssueDate());
	}

	@Transactional
	public SuccessVO insert(String version) {
		Information infomation = new Information();
		infomation.setVersion(version);
		bassInfomationRepository.save(infomation);
		return new SuccessVO();
	}

}
