package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.rss.beans.flux.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


import static org.junit.Assert.*;

/**
 * Created by Alain on 21/08/2016.
 */
public class CustomDateTimeZoneJsonDeserializerTest {


	public static final Logger LOGGER = LoggerFactory.getLogger(CustomDateTimeZoneJsonDeserializerTest.class);

	@Test
	public void deserialize1() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		DateTimeZone date;
		String s="{\"instant\":1428037629,\"offset\":0,\"zoneId\":\"UTC\"}";

		date=mapper.readValue(s,DateTimeZone.class);

		assertNotNull(date);

		LOGGER.info("res="+date);
	}

	@Test
	public void deserialize2() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		DateTimeZone date;
		String s="null";

		date=mapper.readValue(s,DateTimeZone.class);

		assertNull(date);

		LOGGER.info("res="+date);
	}

	@Test
	public void deserialize3() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		DateTimeZone date;
		String s="{\"instant\":1177605663,\"offset\":0,\"zoneId\":\"UTC\"}";

		date=mapper.readValue(s,DateTimeZone.class);

		assertNotNull(date);

		LOGGER.info("res="+date);
	}

}