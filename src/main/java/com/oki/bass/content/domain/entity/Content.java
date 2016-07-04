package com.oki.bass.content.domain.entity;

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
import javax.persistence.Transient;

import com.oki.bass.blog.domain.entity.Blog;
import com.oki.bass.type.BassType;

import lombok.Data;

@Entity
@Table(name = "content")
@Data
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_no", nullable = false)
	private Long contentNo;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;
	
	@Column(name = "category", nullable = false)
	@Enumerated(EnumType.STRING)
	private BassType.CONTENT_CATEGORY category;
	
	@Column(name = "register_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;
	
	@Column(name = "using", nullable = false)
	@Enumerated(EnumType.STRING)
	private BassType.YN using;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id")
	private Blog blog;
	
	@Transient
	private String profile;

	@Override
	public String toString() {
		return "Content [contentNo=" + contentNo + ", url=" + url + ", title=" + title + ", name=" + name
				+ ", thumbnail=" + thumbnail + ", category=" + category + ", registerDate=" + registerDate + ", using="
				+ using + ", profile=" + profile + "]";
	}
	
}
