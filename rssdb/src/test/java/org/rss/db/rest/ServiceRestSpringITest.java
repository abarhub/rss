package org.rss.db.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rss.db.RssdbApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/**
 * Created by Alain on 14/08/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RssdbApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
//@ActiveProfiles("h2")
public class ServiceRestSpringITest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(this.context).build();
	}

	@Test
	public void addUrlNomVideKO() throws Exception {
		this.mockMvc.perform(post("/api3/add_url?name=&url=http%3A%2F%2Ftoto.com%2F"))
				.andExpect(status().is(400))
				.andExpect(content().string("Erreur:parametre name invalide !"));
	}

	@Test
	public void addUrlUrlVideKO() throws Exception {
		this.mockMvc.perform(post("/api3/add_url?name=Abc&url="))
				.andExpect(status().is(400))
				.andExpect(content().string("Erreur:parametre url invalide !"));
	}

	@Test
	public void addUrlOK() throws Exception {
		this.mockMvc.perform(
				post("/api3/add_url?name=AAA&url=http%3A%2F%2Ftoto2.com%2F"))
				.andExpect(status().is(200))
				.andExpect(content().string("OK"));
	}

	@Test
	public void addUrl2OK() throws Exception {
		this.mockMvc.perform(
				post("/api3/add_url")
						.param("name","AAA")
						.param("url","http%3A%2F%2Ftoto2.com%2F"))
				.andExpect(status().is(200))
				.andExpect(content().string("OK"));
	}
}