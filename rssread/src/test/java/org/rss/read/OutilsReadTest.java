package org.rss.read;

import com.google.common.base.Preconditions;
import org.junit.Test;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Alain on 21/08/2016.
 */
public class OutilsReadTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(OutilsRead.class);

	@Test
	public void conv() throws Exception {

	}

	@Test
	public void convDate() throws Exception {

		DateTimeZone res,ref;
		OutilsRead outilsRead=new OutilsRead();

		res = outilsRead.convDate("");
		assertNull(res);

		res = outilsRead.convDate(null);
		assertNull(res);

		res = outilsRead.convDate("Mon, 30 Sep 2002 11:00:00 GMT");
		//LOGGER.info("res="+res.getDate().getTime());
		LOGGER.info("res2="+res);
		//ref=new ZonedDateTime(new Date(1033376400000L));
		ref=new DateTimeZone(OutilsGeneriques.getDateUTC(2002,9,30,11,0,0));
		LOGGER.info("ref="+ref);
		assertEquals(ref,res);
	}


}