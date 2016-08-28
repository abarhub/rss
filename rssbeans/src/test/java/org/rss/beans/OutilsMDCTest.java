package org.rss.beans;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.rss.beans.serializerDeserializer.CustomDateTimeZoneJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Alain on 28/08/2016.
 */
public class OutilsMDCTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(OutilsMDCTest.class);

	@Before
	public void init(){
		for(MDCKey key:MDCKey.values()) {
			OutilsMDC.removeMdc(key);
		}
	}

	@Test
	public void addMdc() throws Exception {
		isMDCVide();
		OutilsMDC.addMdc(MDCKey.SERVEUR,"AAA");
		LOGGER.info("ABC");
		isMDCEgal(newMap(new Object[][]{
				{MDCKey.SERVEUR,"AAA"},
		}));
		OutilsMDC.addMdc(MDCKey.GET_URL,"http://");
		LOGGER.info("ABC 2");
	}

	@Test
	public void removeMdc() throws Exception {
		isMDCVide();
		OutilsMDC.addMdc(MDCKey.SERVEUR,"BB");
		LOGGER.info("Test 1");
		OutilsMDC.addMdc(MDCKey.GET_URL,"CCC");
		LOGGER.info("Test 2");
		OutilsMDC.removeMdc(MDCKey.SERVEUR);
		LOGGER.info("Test 3");
	}

	private void isMDCVide(){
		Map<String,String> map;
		map=OutilsMDC.getCopyMDC();
		map=Maps.newHashMap(map);
		map.remove(OutilsMDC.CONTEXT_REGROUPE);
		assertTrue(map.isEmpty());
	}

	private void isMDCEgal(final Map<MDCKey,String> map){
		Map<String,String> mapRef;
		assertNotNull(map);
		assertFalse(map.isEmpty());
		mapRef=OutilsMDC.getCopyMDC();
		assertTrue(mapRef.containsKey(OutilsMDC.CONTEXT_REGROUPE));
		mapRef=Maps.newHashMap(mapRef);
		mapRef.remove(OutilsMDC.CONTEXT_REGROUPE);

		for(Map.Entry<MDCKey,String> iter:map.entrySet()){
			String key=iter.getKey().getKeyName();
			String value=iter.getValue();
			assertTrue("key="+key+"("+mapRef+")",mapRef.containsKey(key));
			assertEquals(value,mapRef.get(key));
		}
		assertEquals(map.size(),mapRef.size());
	}

	private Map<MDCKey,String> newMap(Object[][] tab){
		Map<MDCKey,String> res;
		assertNotNull(tab);
		assertTrue(tab.length>0);
		res= Maps.newHashMap();
		for(Object[]tab2:tab){
			assertEquals(2,tab2.length);
			assertNotNull(tab2[0]);
			assertNotNull(tab2[1]);
			assertTrue(tab2[0] instanceof MDCKey);
			assertTrue(tab2[1] instanceof String);
			MDCKey key=(MDCKey)tab2[0];
			assertNotNull(key);
			String value=(String)tab2[1];
			res.put(key,value);
		}
		return res;
	}
}