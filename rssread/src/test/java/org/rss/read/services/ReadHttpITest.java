package org.rss.read.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Alain on 28/08/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadHttpITest {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReadHttpITest.class);

	@Autowired
	private ReadHttp readHttp;

	@LocalServerPort
	private int port;

	@Test
	public void readOK() throws Exception {
		LOGGER.info("Port={}",port);
		LOGGER.info("Lecture du flux ...");
		String res=readHttp.read("http://localhost:"+port+"/read/index.html");
		LOGGER.info("fin de lecture du flux");
		assertNotNull(res);
		String def="<!DOCTYPE html>\r\n" +
				"<html lang=\"en\">\r\n" +
				"<head>\r\n" +
				"    <meta charset=\"UTF-8\">\r\n" +
				"    <title>Title</title>\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"\r\n" +
				"</body>\r\n" +
				"</html>";
		assertEquals(def,res);
	}
}