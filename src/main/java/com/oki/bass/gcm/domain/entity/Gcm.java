package com.oki.bass.gcm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "gcm")
@Data
public class Gcm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gcm_no", nullable = false)
	private Long gcmNo;
	
	@Column(name = "gcm", nullable = false)
	private String gcm;
	
}
