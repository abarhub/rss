package org.rss.beans.flux;

import org.junit.Test;
import org.rss.beans.OutilsGeneriques;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by Alain on 21/08/2016.
 */
public class DateTimeZoneTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(DateTimeZoneTest.class);

	@Test
	public void test1UTC(){
		DateTimeZone d3,d4;
		ZonedDateTime z1,z2;

		z1= OutilsGeneriques.getDateUTC(2005,4,3,7,14,32);
		d3=new DateTimeZone(z1);
		z2= OutilsGeneriques.getDateUTC(2005,4,3,7,14,32);
		d4=new DateTimeZone(z2);
		assertEquals(d3,d4);

		assertEquals(d3,d3.toUTC());

		assertEquals(d3.toUTC(),d3.toUTC());

		assertEquals(d3.toDateUTC(),d3.toDateUTC());

		d4=new DateTimeZone(d3.toDateUTC());
		assertEquals(d3,d4);

		assertEquals(0,d3.compareTo(d4));
		assertEquals(0,d4.compareTo(d3));

		String affichage=d3.format("hh:mm:ss dd/MM/yyyy", Locale.FRANCE);
		assertEquals("07:14:32 03/04/2005",affichage);
		LOGGER.info("affichage="+affichage);

		String toString=d3.toString();
		assertEquals("DateTimeZone{date=2005-04-03T07:14:32Z[UTC]}",toString);
		LOGGER.info("toString="+toString);
	}

	@Test
	public void test2EuropeParis(){
		DateTimeZone d3,d4;
		ZonedDateTime z1,z2;

		z1= OutilsGeneriques.getDate(2004,8,16,9,27,45,"Europe/Paris");
		d3=new DateTimeZone(z1);
		z2= OutilsGeneriques.getDate(2004,8,16,9,27,45,"Europe/Paris");
		d4=new DateTimeZone(z2);
		assertEquals(d3,d4);

		assertNotSame(d3,d3.toUTC());

		assertEquals(d3.toUTC(),d3.toUTC());

		assertEquals(d3.toDateUTC(),d3.toDateUTC());

		assertEquals(0,d3.compareTo(d4));
		assertEquals(0,d4.compareTo(d3));

		d4=new DateTimeZone(d3.toDateUTC());
		assertEquals(d3.toUTC(),d4);

		String affichage=d3.format("hh:mm:ss dd/MM/yyyy", Locale.FRANCE);
		assertEquals("09:27:45 16/08/2004",affichage);
		LOGGER.info("affichage="+affichage);

		String toString=d3.toString();
		assertEquals("DateTimeZone{date=2004-08-16T09:27:45+02:00[Europe/Paris]}",toString);
		LOGGER.info("toString="+toString);
	}


}