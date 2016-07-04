package com.oki.bass.youtube.domain.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oki.bass.type.BassType;

import lombok.Data;

@Entity
@Table(name = "youtube")
@Data
public class Youtube {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "youtube_no", nullable = false)
	private Long youtubeNo;
	
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@JsonIgnore
	@Column(name = "description", nullable = false, length = 2000)
	private String description;
	
	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;
	
	@Column(name = "video_id", nullable = false)
	private String videoId;
	
	@Column(name = "duration", nullable = false)
	private Long duration;
	
	@Column(name = "profile", nullable = false)
	private String profile;
	
	@Column(name = "published_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishedDate;
	
	@Column(name = "view", nullable = false)
	@Enumerated(EnumType.STRING)
	private BassType.YN view;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "playlist_id", referencedColumnName = "id")
	private YoutubeChannel youtubeChannel;
	
}
