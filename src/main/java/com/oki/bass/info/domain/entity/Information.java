package com.oki.bass.info.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "information")
@Data
public class Information {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "info_no", nullable = false)
	private Long infoNo;
	
	@Column(name = "version", nullable = false)
	private String version;
	
	@Column(name = "issue", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueDate = new Date();

}
