package com.oki.bass.info.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oki.bass.info.domain.entity.Information;

public interface BassInformationRepository extends JpaRepository<Information, Long>{

	Information findFirstByOrderByIssueDateDesc();

}
