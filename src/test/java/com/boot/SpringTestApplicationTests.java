package com.boot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.boot.base.BassStoryTestInitializer;
import com.oki.BassApplication;
import com.oki.bass.content.domain.entity.Content;
import com.oki.bass.content.domain.repository.BassContentRepository;
import com.oki.bass.gcm.domain.entity.Gcm;
import com.oki.bass.gcm.domain.repository.BassGcmRepository;
import com.oki.bass.gcm.vo.BassGcmInsertVO;
import com.oki.bass.info.domain.entity.Information;
import com.oki.bass.info.domain.repository.BassInformationRepository;
import com.oki.bass.type.BassType;
import com.oki.bass.youtube.domain.entity.Youtube;
import com.oki.bass.youtube.domain.entity.YoutubeChannel;
import com.oki.bass.youtube.domain.repository.BassYoutubeChannelRepository;
import com.oki.bass.youtube.domain.repository.BassYoutubeRepository;
import com.oki.util.json.JsonUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BassApplication.class, initializers = BassStoryTestInitializer.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles(value = "local")
@Rollback
@Transactional
public class SpringTestApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private BassGcmRepository bassGcmRepository;
	
	@Autowired
	private BassContentRepository bassContentRepository;
	
	@Autowired
	private BassYoutubeRepository bassYoutubeRepository;
	
	@Autowired
	private BassYoutubeChannelRepository bassYoutubeChannelRepository;
	
	@Autowired
	private BassInformationRepository bassInformationRepository;
	
	private MockMvc mockMvc;
	
	@Before
	public void before(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@Test
	public void test_gcm() throws Exception {
		// parameter
		BassGcmInsertVO bassGcmInsertVO = new BassGcmInsertVO();
		bassGcmInsertVO.setGcm("test_gcm");
		
		String content = JsonUtils.toJson(bassGcmInsertVO);
		
		// request post
		mockMvc.perform(
				post("/gcm/insert")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andDo(print())
					.andExpect(status().is2xxSuccessful());
		
		List<Gcm> gcms = bassGcmRepository.findAll();
		for (Gcm gcm : gcms) {
			System.out.println(gcm);
		}
		Assert.assertEquals(1, gcms.size());
	}
	
	@Test
	public void test_content() throws Exception {
		Content content = new Content();
		content.setCategory(BassType.CONTENT_CATEGORY.FISHING);
		content.setName("test name" + Math.random());
		content.setRegisterDate(new Date());
		content.setThumbnail("test thumbnail" + Math.random());
		content.setTitle("test title" + Math.random());
		content.setUrl("test url" + Math.random());
		
		bassContentRepository.save(content);
		
		// request post
		mockMvc.perform(
				get("/content/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andDo(print())
					.andExpect(status().is2xxSuccessful());
		
	}
	
	@Test
	public void test_youtube() throws Exception {
		YoutubeChannel youtubeChannel = new YoutubeChannel();
		youtubeChannel.setDescription("description");
		youtubeChannel.setIsUse(BassType.YN.Y);
		youtubeChannel.setRegisteredDate(new Date());
		youtubeChannel.setTitle("title");
		youtubeChannel.setId("id");
		
		YoutubeChannel savedYoutubeChannel = bassYoutubeChannelRepository.save(youtubeChannel);
		
		Youtube youtube = new Youtube();
		youtube.setDescription("description");
		youtube.setDuration(10L);
		youtube.setProfile("profile");
		youtube.setPublishedDate(new Date());
		youtube.setThumbnail("thumbnail");
		youtube.setTitle("title");
		youtube.setVideoId("videoId");
		youtube.setView(BassType.YN.Y);
		youtube.setId("id");
		youtube.setYoutubeChannel(savedYoutubeChannel);
		
		bassYoutubeRepository.save(youtube);
		
		// request post
		mockMvc.perform(
				get("/youtube/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(print())
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void test_information() throws Exception {
		Information informationFirst = new Information();
		informationFirst.setVersion("first");
		
		Information informationSecond = new Information();
		informationSecond.setVersion("second");
		
		bassInformationRepository.save(informationFirst);
		bassInformationRepository.save(informationSecond);
		
		// request post
		mockMvc.perform(
				get("/info/version")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andDo(print())
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("$.version").value("first"));
		
	}
	
	@Test
	public void test_information_insert() throws Exception {
		String content = JsonUtils.toJson("version");
		
		// request post
		mockMvc.perform(
				put("/info/version")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());
		
	}

}
