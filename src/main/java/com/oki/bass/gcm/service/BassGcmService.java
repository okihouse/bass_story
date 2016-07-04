package com.oki.bass.gcm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oki.bass.gcm.domain.entity.Gcm;
import com.oki.bass.gcm.domain.repository.BassGcmRepository;
import com.oki.bass.gcm.vo.BassGcmInsertVO;

@Service
public class BassGcmService {

	@Autowired
	private BassGcmRepository bassGcmRepository;
	
	@Transactional
	public void insert(BassGcmInsertVO bassGcmInsertVO) {
		try {
			List<Gcm> gcms = bassGcmRepository.findByGcm(bassGcmInsertVO.getGcm());
			if (gcms.size() > 0) return;
			
			Gcm gcm = new Gcm();
			gcm.setGcm(bassGcmInsertVO.getGcm());
			
			bassGcmRepository.save(gcm);
		} catch (Exception e) {
			throw e;
		}
	}

}
