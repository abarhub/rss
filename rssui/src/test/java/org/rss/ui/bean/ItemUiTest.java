package org.rss.ui.bean;

import org.junit.Test;
import org.rss.beans.OutilsGeneriques;
import org.rss.beans.flux.DateTimeZone;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Alain on 21/08/2016.
 */
public class ItemUiTest {

	@Test
	public void test1(){
		ItemUi itemUi;
		itemUi=new ItemUi();
		itemUi.setTitle("AAA");
		itemUi.setPubDate("7456132");
		itemUi.setDescription("BBBBB");
		itemUi.setGuid("951234");
		itemUi.setLink("http://test/745");
		DateTimeZone dateTimeZone=new DateTimeZone(OutilsGeneriques.getDateUTC(2011,4,3,7,8,9));
		itemUi.setPubDate2(dateTimeZone);

		assertEquals("AAA",itemUi.getTitle());
		assertEquals("7456132",itemUi.getPubDate());
		assertEquals("BBBBB",itemUi.getDescription());
		assertEquals("951234",itemUi.getGuid());
		assertEquals("http://test/745",itemUi.getLink());
		dateTimeZone=new DateTimeZone(OutilsGeneriques.getDateUTC(2011,4,3,7,8,9));
		assertEquals(dateTimeZone,itemUi.getPubDate2());
	}

}