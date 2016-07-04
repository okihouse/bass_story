package com.oki.bass.blog.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.oki.bass.content.domain.entity.Content;
import com.oki.bass.type.BassType;

import lombok.Data;

@Entity
@Table(name = "blog")
@Data
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blog_no")
	private Long blogNo;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "profile", nullable = false)
	private String profile;
	
	@Column(name = "using", nullable = false)
	@Enumerated(EnumType.STRING)
	private BassType.YN using;
	
	@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Content> contents = new ArrayList<>();

	@Override
	public String toString() {
		return "Blog [blogNo=" + blogNo + ", name=" + name + ", title=" + title + ", url=" + url + ", id=" + id
				+ ", profile=" + profile + "]";
	}
	
}
