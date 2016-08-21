
package org.rss.beans.serializerDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


import static org.junit.Assert.*;

/**
 * Created by Alain on 21/08/2016.
 */
public class CustomDateTimeZoneJsonSerializerTest {


	public static final Logger LOGGER = LoggerFactory.getLogger(CustomDateTimeZoneJsonSerializerTest.class);

	@Test
	public void serializeUTC1() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDateUTC(2015,4,3,5,7,9));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		assertEquals("{\"instant\":1428037629,\"offset\":0,\"zoneId\":\"UTC\"}",out.toString());

		LOGGER.info("json="+out.toString());
	}

	@Test
	public void serializeParis2() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDate(2010,8,19,15,37,58,"Europe/Paris"));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		String res=out.toString();
		assertEquals("{\"instant\":1282232278,\"offset\":7200,\"zoneId\":\"Europe/Paris\"}",res);

		LOGGER.info("json="+res);

		ObjectMapper mapper2 = new ObjectMapper();

		DateTimeZone date2=mapper2.readValue(res,DateTimeZone.class);

		assertNotNull(date2);

		LOGGER.info("date="+date);
		LOGGER.info("date2="+date2);

		assertEquals(date,date2);
		assertEquals(date.toString(),date2.toString());
	}

	@Test
	public void serializeBerlin3() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDate(2008,7,21,16,48,3,"Europe/Berlin"));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		String res=out.toString();
		assertEquals("{\"instant\":1216658883,\"offset\":7200,\"zoneId\":\"Europe/Berlin\"}",res);

		LOGGER.info("json="+res);

		ObjectMapper mapper2 = new ObjectMapper();

		DateTimeZone date2=mapper2.readValue(res,DateTimeZone.class);

		assertNotNull(date2);

		LOGGER.info("date="+date);
		LOGGER.info("date2="+date2);

		assertEquals(date,date2);
		assertEquals(date.toString(),date2.toString());
	}

	@Test
	public void serializeReunion4() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDate(2005,2,28,16,57,1,"Indian/Reunion"));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		String res=out.toString();
		assertEquals("{\"instant\":1109609821,\"offset\":14400,\"zoneId\":\"Indian/Reunion\"}",res);

		LOGGER.info("json="+res);

		ObjectMapper mapper2 = new ObjectMapper();

		DateTimeZone date2=mapper2.readValue(res,DateTimeZone.class);

		assertNotNull(date2);

		LOGGER.info("date="+date);
		LOGGER.info("date2="+date2);

		assertEquals(date,date2);
		assertEquals(date.toString(),date2.toString());
	}

	@Test
	public void serializeArizona5() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDate(2003,4,26,16,41,3,"US/Arizona"));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		String res=out.toString();
		assertEquals("{\"instant\":1051375263,\"offset\":-25200,\"zoneId\":\"US/Arizona\"}",res);

		LOGGER.info("json="+res);

		ObjectMapper mapper2 = new ObjectMapper();

		DateTimeZone date2=mapper2.readValue(res,DateTimeZone.class);

		assertNotNull(date2);

		LOGGER.info("date="+date);
		LOGGER.info("date2="+date2);

		assertEquals(date,date2);
		assertEquals(date.toString(),date2.toString());
	}

	@Test
	public void serializeUTC6() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		final DateTimeZone date=new DateTimeZone(OutilsGeneriques.getDateUTC(2006,4,26,16,41,3));

		Writer out=new StringWriter();
		mapper.writeValue(out,date);

		String res=out.toString();
		assertEquals("{\"instant\":1146069663,\"offset\":0,\"zoneId\":\"UTC\"}",res);

		LOGGER.info("json="+res);

		ObjectMapper mapper2 = new ObjectMapper();

		DateTimeZone date2=mapper2.readValue(res,DateTimeZone.class);

		assertNotNull(date2);

		LOGGER.info("date="+date);
		LOGGER.info("date2="+date2);

		assertEquals(date,date2);
		assertEquals(date.toString(),date2.toString());
	}

}