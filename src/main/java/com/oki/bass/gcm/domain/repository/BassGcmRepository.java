package com.oki.bass.gcm.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oki.bass.gcm.domain.entity.Gcm;

public interface BassGcmRepository extends JpaRepository<Gcm, Long>{

	List<Gcm> findByGcm(String gcm);

}
