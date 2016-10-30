package org.rss.ui.service;

import org.junit.Before;
import org.junit.Test;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;

import static org.junit.Assert.*;

/**
 * Created by Alain on 21/08/2016.
 */
public class UIServiceTest {

	private UIService uiService;

	@Before
	public void init(){
		uiService=new UIService();
	}

	@Test
	public void convDate() throws Exception {
		DateTimeZone date;
		date= new DateTimeZone(OutilsGeneriques.getDateUTC(2014,3,26,18,4,58));
		String res=uiService.convDate(date);

		assertEquals("18:04:58 26/03/2014",res);
	}

}