package com.oki.bass.youtube.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.oki.bass.type.BassType;

import lombok.Data;

@Entity
@Table(name = "youtube_channel")
@Data
public class YoutubeChannel implements Serializable {

	private static final long serialVersionUID = 4740055881123915909L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "youtube_channel_no", nullable = false)
	private Long youtubeChannelNo;
	
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "registered_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDate;
	
	@Column(name = "isUse", nullable = false)
	@Enumerated(EnumType.STRING)
	private BassType.YN isUse;
	
	@OneToMany(mappedBy = "youtubeChannel", fetch = FetchType.LAZY)
	private List<Youtube> youtubes = new ArrayList<>();
	
}
